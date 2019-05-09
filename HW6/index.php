<?php
$keyword = $category = $new = $used = $unspecified = $localpickup = $freeshipping = $nearby = $miles = $locations = $zipcode = "";
$zipErr = $mileErr = "";
$itemId="";
$appid = "ZiyueYan-ZYproduc-PRD-816db7c91-02b29d47";

function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

if($_SERVER["REQUEST_METHOD"] == "POST") {
    $keyword = test_input($_POST["keyword"]);
    $category = test_input($_POST["category"]);
    if($category == 'Art') $categoryId = 550; else if($category == 'Baby') $categoryId = 2984; else if($category == 'Books') $categoryId = 267; else if($category == 'Clothing' || $category == 'Shoes &amp; Accessories') $categoryId = 11450; else if($category == 'Computers/Tablets &amp; Networking') $categoryId = 58058; else if($category == 'Health &amp; Beauty') $categoryId = 26395; else if($category == 'Music') $categoryId = 11233; else if($category == 'Video Games &amp; Consoles') $categoryId = 1249; else $categoryId = -1;
    if(isset($_POST["new"])) $new = test_input($_POST["new"]);
    if(isset($_POST["used"])) $used = test_input($_POST["used"]);
    if(isset($_POST["unspecified"])) $unspecified = test_input($_POST["unspecified"]);
    if(isset($_POST["localpickup"])) $localpickup = test_input($_POST["localpickup"]);
    if(isset($_POST["freeshipping"])) $freeshipping = test_input($_POST["freeshipping"]);
    if(isset($_POST["nearby"])) {
        $nearby = test_input($_POST["nearby"]);
        if($_POST["miles"] != "") $miles = test_input($_POST["miles"]);
        else $miles = 10;
        $locations = test_input($_POST["locations"]);
        if($locations == "here") {
            $zipcode = test_input($_POST["herezipcode"]);
        }
        else {
            $zipcode = test_input($_POST["zipcode"]);
            if(!preg_match("/^[0-9]{5}$/", $zipcode)) {
                $zipErr="Zipcode is invalid";
                exit($zipErr);
            }
        }
    }
    
    //serarch button $_POST["submit"], JSON response returned by the eBay Finding API
    if(isset($_POST["submit"])) {
        $searchUrl = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=".$appid."&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=20";
        $searchUrl .= "&keywords=".urlencode($keyword);
        if($categoryId != -1) $searchUrl .= "&categoryId=".$categoryId;
        $count = 0;
        if(isset($_POST["nearby"])) {
            $searchUrl .="&buyerPostalCode=".$zipcode;
            $searchUrl .="&itemFilter(".$count.").name=MaxDistance&itemFilter(".$count.").value=".urlencode($miles);
            $count++;
        }
        if(isset($_POST["localpickup"])) {
            $searchUrl .="&itemFilter(".$count.").name=LocalPickupOnly&itemFilter(".$count.").value=true";
            $count++;
        }
        if(isset($_POST["freeshipping"])) {
            $searchUrl .="&itemFilter(".$count.").name=FreeShippingOnly&itemFilter(".$count.").value=true";
            $count++;
        }
        if(isset($_POST["new"]) || isset($_POST["used"]) || isset($_POST["unspecified"])) {
            $searchUrl .= "&itemFilter(".$count.").name=Condition";
            $value_count = 0;
            if(isset($_POST["new"])) {
                $searchUrl .="&itemFilter(".$count.").value(".$value_count.")=New";
                $value_count++;
            }
            if(isset($_POST["used"])) {
                $searchUrl .= "&itemFilter(".$count.").value(".$value_count.")=Used";
                $value_count++;
            }
            if(isset($_POST["unspecified"])) {
                $searchUrl .="&itemFilter(".$count.").value(".$value_count.")=Unspecified";
            }
        }
        //echo $searchUrl;
        $resultJSON = file_get_contents($searchUrl);
        //echo $resultJSON;
        exit($resultJSON);
    }
    
    //search details request, request for the detailed information using the eBay shopping API.
    if(isset($_POST["itemId"])){
        $itemId = test_input($_POST["itemId"]);
        $detailUrl = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=".$appid."&siteid=0&version=967&ItemID=".$itemId."&IncludeSelector=Description,Details,ItemSpecifics";
        $detailJSON = file_get_contents($detailUrl);
        exit($detailJSON);
    }
    
    //eBay Merchandise API that searches for similar products to the product.
    if(isset($_POST["similar"])) {
        $itemId = test_input($_POST["similar"]);
        $similarUrl = "http://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID=".$appid."&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId=".$itemId."&maxResults=8";
        $similarJSON = file_get_contents($similarUrl);
        exit($similarJSON);
    }
}

else { 
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Product Search</title>
        <style type="text/css">
            body {
                text-align: center;
                font-family: serif;
            }
            
            #product_search {
                border: 3px;
                border-color: rgb(180, 180, 180);
                border-style: solid;
                background-color: rgb(250,250,250);
                height: 290px;
                width: 640px;
                margin: auto;
                padding: 10px;
                text-align: left;
                line-height: 33px;
            }
            
            #form_title {
                font-family: serif;
                font-size: 36px;
                text-align: center;
                margin: 1px;
                padding-bottom: 5px;
            }
            
            hr {
                color: rgb(180, 180, 180);
            }
            
            #search_form {
                margin-left: 10px;
                font-size: 17px;
            }
            
            #category {
                width: 240px;
            }
            
            .condition {
                margin-left: 23px;
            }
            
            .shipping {
                margin-left: 44px;
            }
            
            .grey {
                opacity:0.5;
            }
            
            #miles {
                margin-left: 25px;
                margin-right: 7px;
                width: 45px;
            }
            
            #from {
                list-style-type: none;
                margin: 0;
                padding: 0;
                display: inline;
                position: absolute;
            }
            
            #search_buttons {
                margin-top: 30px;
                margin-left: -70px;
                text-align: center;
            }
            
            .error {
                width: 750px;
                margin: auto;
                margin-top: 30px;
                border: 1px;
                border-color: rgb(180,180,180);
                border-style: solid;
                background-color: rgb(230,230,230);
            }
            
            #search_result {
                margin: auto;
            }
            
            #search_result table {
                margin: auto;
                border-collapse: collapse;
                margin-top: 0;
                width: 90%;
            }
            
            table td, table th {
                border: 2px solid #ccc;
                text-align: left;
            }
            
            #result th {
                text-align: center;
            }
            
            a:hover {
                opacity:0.5;
            }

            
            #detail caption {
                font-size: 30px;
            }
            
            #detail {
                max-width: 950px;
            }
            
            .click {
                font-size: 17px;
                color: rgb(180,180,180);
            }
            
            .arrow {
                width: 50px;
                height: 25px;
            }
            
            #similar_div {
                margin: auto;
                width: 700px;
                overflow-x: scroll;
                border: 2px solid #ccc;
            }
            
            #similar_div table {
                border: 0;
            }
            
            #similar_div td {
                width: 150px;
                text-align: center;
                border: 0;
                padding-left: 25px;
                padding-top: 10px;
            }
            
            .no_similar {
                width: 750px;
                margin: auto;
                margin-top: 20px;
                border: 2px solid #ccc;
            }
            .no_similar p {
                border: 1px solid #ccc;
                margin-left: 7px;
                margin-top: 7px;
                margin-bottom: 7px;
                margin-right: 0;
                padding-right: 0;
                border-right: 0;
            }
        </style>
    </head>
    
    <body>
        <div id="product_search">
            <p id="form_title"><i>Product Search</i></p>
            <hr>
            
            <form id="search_form" method="post" action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']);?>">
                <b>Keyword </b><input id="keyword" name="keyword" type="text" required="required" value="">
                <br>
                
                <b>Category</b>
                <select id="category" name="category">
                    <option value="All Categories">All Categories</option>
                    <option value="" disabled>------------------------------</option>
                    <option value="Art">Art</option>
                    <option value="Baby">Baby</option>
                    <option value="Books">Books</option>
                    <option value="Clothing">Clothing</option>
                    <option value="Shoes & Accessories">Shoes &amp; Accessories</option>
                    <option value="Computers/Tablets & Networking">Computers/Tablets &amp; Networking</option>
                    <option value="Health & Beauty">Health &amp; Beauty</option>
                    <option value="Music">Music</option>
                    <option value="Video Games & Consoles">Video Games &amp; Consoles</option>
                </select>
                <br>
                
                <b>Condition</b>
                    <input class="condition" id="new" name="new" type="checkbox" value="yes">New
                    <input class="condition" id="used" name="used" type="checkbox" value="yes">Used
                    <input class="condition" id="unspecified" name="unspecified" type="checkbox" value="yes">Unspecified
                <br>
                
                <b>Shipping Options</b>
                    <input class="shipping" id="localpickup" name="localpickup" type="checkbox" value="yes">Local Pickup
                    <input class="shipping" id="freeshipping" name="freeshipping" type="checkbox" value="yes">Free Shipping
                <br>
                
                <input id="nearby" name="nearby" type="checkbox"><b>&nbsp;&nbsp;Enable Nearby Search</b>
                <input class="grey" id="miles" name="miles" type="text" placeholder="10" disabled> 
                <span class="grey"><b>miles from</b></span>
                <ul class="grey" id="from" >
                    <li>
                        <input id="here" name="locations" type="radio" value="here" disabled checked>&nbsp;Here
                        <input type="hidden" id="herezipcode" name="herezipcode">
                    </li>
                    <li>
                        <input id="zip" name="locations" type="radio" value="zip" disabled>
                        <input class="grey" id="zipcode" name="zipcode" type="text" placeholder="zip code" required="required" disabled>
                    </li>
                </ul>
                <br>
                
                <div id="search_buttons">
                    <button id="submit" name="submit" type="submit" value="Submit">Search</button>
                    <button id="clear" name="clear" type="reset" value="Clear" onclick="clear_Result()">Clear</button>
                </div>
                
            </form>
        </div>
        <p></p>
        <div id="search_result"></div>
    
    <script type="text/javascript">
        //for Nearby Search disable/enable
        var nearby = document.getElementById("nearby");
        var miles = document.getElementById("miles");
        var here = document.getElementById("here");
        var zip = document.getElementById("zip");
        var zipcode = document.getElementById("zipcode");
        var greys = document.getElementsByClassName("grey");
        nearby.addEventListener("change", function(){
            if(this.checked){
                for(var i = 0; i < greys.length; i++) {
                    greys[i].style.opacity=1;
                }
                miles.disabled = false;
                here.disabled = false;
                zip.disabled = false;
            }
            else {
                for(var i = 0; i < greys.length; i++) {
                    greys[i].style.opacity=0.5;
                }
                miles.disabled = true;
                here.disabled = true;
                zip.disabled = true;
                zipcode.disabled = true;
            }
        });
        here.addEventListener("change", function() {
            if(this.checked) {
                zipcode.disabled = true;
                zipcode.value = "";
            }
        });
        zip.addEventListener("change", function() {
            if(this.checked) {
                zipcode.style.opacity=1;
                zipcode.disabled = false;
            }
        });

        //for Clear Button
        function clear_Result(){
            document.getElementById("search_form").reset();
            var greys = document.getElementsByClassName("grey");
            for(var i = 0; i < greys.length; i++) {
                greys[i].style.opacity=1;
            }
            document.getElementById("miles").disabled = true;
            document.getElementById("here").disabled = true;
            document.getElementById("zip").disabled = true;
            document.getElementById("zipcode").disabled = true;
            remove_children("search_result");
        }
        
        function remove_children(id) {
            console.log("shoud remove result");
            var element = document.getElementById(id);
            while (element && element.firstChild) {
                element.removeChild(element.firstChild);
            }
        }
        
        function bad_request(){
            var err = document.createElement("div");
            err.innerHTML = "Bad request! Unparse JSON!";
            err.className = "error";
            document.getElementById("search_result").appendChild(err);
            return;
        }
    </script>
    
    <script type="text/javascript">
        //use ip-api.com for searching the zip code based on IP addresses
        var xmlhttp=new XMLHttpRequest(); 
        xmlhttp.open("GET", "http://ip-api.com/json", false);
        xmlhttp.onreadystatechange = function () {
            if(xmlhttp.readyState === 4) {
                if(xmlhttp.status === 200) {
                    jsonDoc = xmlhttp.responseText;
                }
                else if (xmlhttp.status==404){
                    window.alert("can not get zip code from IP address! Try Again");
                }
            }
        }
        xmlhttp.send();
        var herezipJson= JSON.parse(xmlhttp.responseText);
        console.log(herezipJson);
        document.getElementById("herezipcode").value = herezipJson.zip;
        console.log(herezipJson.zip);
    </script>
    
    <script type="text/javascript">
        //----------------------------------------------------------------------submit for search
        document.forms[0].addEventListener("submit", function(event) {   
            if(event.preventDefault){
               event.preventDefault();
            }
            else{
                window.event.returnValue=false;
            }
            var params = "";
            var data = new FormData(this);
            for (const entry of data) {
                params += entry[0] + "=" + entry[1] + "&";
            }
            params += "submit=submit";
            var xmlhttp=new XMLHttpRequest(); 
            xmlhttp.open("POST", this.action, false);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send(params);
            console.log(xmlhttp.responseText);
            if(xmlhttp.responseText == "Zipcode is invalid") {
                remove_children("search_result");
                var ziperr = document.createElement("div");
                ziperr.innerHTML = "Zipcode is invalid";
                ziperr.className = "error";
                document.getElementById("search_result").appendChild(ziperr);
                return;
            }
            else {
                remove_children("search_result");
                try {
                    var resultJSON = JSON.parse(xmlhttp.responseText);
                }
                catch {
                    remove_children("search_result");
                    bad_request();
                    return;
                }
                console.log(resultJSON);
                show_search_result(resultJSON);
            }
        }, false);
        
        //-----------------------------------------------------------------------------show search result in table
        function show_search_result(resultJSON){
            if(resultJSON.findItemsAdvancedResponse[0].ack[0] == "Failure" && resultJSON.findItemsAdvancedResponse[0].errorMessage[0].error[0].message[0] == "Invalid postal code for specified country.") {
                remove_children("search_result");
                var ziperr = document.createElement("div");
                ziperr.innerHTML = "Zipcode is invalid";
                ziperr.className = "error";
                document.getElementById("search_result").appendChild(ziperr);
                return;
            }
            if(resultJSON.findItemsAdvancedResponse[0].ack[0] == "Failure") {
                var err = document.createElement("div");
                err.innerHTML = "A ErrorMessage JSON returned!";
                if(resultJSON.findItemsAdvancedResponse[0].errorMessage[0].error[0]) err.innerHTML += "The message is: " + resultJSON.findItemsAdvancedResponse[0].errorMessage[0].error[0].message[0];
                err.className = "error";
                document.getElementById("search_result").appendChild(err);
                return;
            }
            try {
                searchresult = resultJSON.findItemsAdvancedResponse[0].searchResult[0];
            }
            catch {
                var err = document.createElement("div");
                err.innerHTML = "A ErrorMessage JSON returned!";
                err.className = "error";
                document.getElementById("search_result").appendChild(err);
                return;
            }
            if(searchresult["@count"] == "0") {
                var norecord = document.createElement("div");
                norecord.innerHTML = "No Records has been found";
                norecord.className = "error";
                document.getElementById("search_result").appendChild(norecord);
                return;
            }
            var items_list = searchresult.item; //arrylist
            var result_html = "<table id='result'><tr><th>Index</th><th>Photo</th><th>Name</th><th>Price</th><th>Zip&nbsp;code<th>Condition</th><th>Shipping&nbsp;Option</th>";
            for(var i = 0; i < items_list.length; i++) {
                var item = items_list[i];
                index = i+1;
                result_html += "<tr><td>"+index+"</td>";
                if(item.galleryURL)
                    result_html += "<td><img src='" + item.galleryURL[0] +"'></td>";
                else 
                    result_html += "<td>N/A</td>";
                if(item.title) 
                    result_html += "<td><a onclick=search_detail(" + item.itemId[0]+ ")>" + item.title[0] +"</a></td>";
                else 
                    result_html += "<td>N/A</td>";
                if(item.sellingStatus[0].currentPrice) 
                    result_html += "<td>$" + item.sellingStatus[0].currentPrice[0].__value__ +"</td>";//need to change $? .@currencyId
                else 
                    result_html += "<td>N/A</td>";
                if(item.postalCode)
                    result_html += "<td>" + item.postalCode[0] + "</td>";
                else 
                    result_html += "<td>N/A</td>";
                if(item.condition)
                    result_html += "<td>" + item.condition[0].conditionDisplayName +"</td>";
                else 
                    result_html += "<td>N/A</td>";
                if(item.shippingInfo[0].shippingServiceCost) {
                    if(item.shippingInfo[0].shippingServiceCost[0].__value__ == "0.0") 
                        result_html += "<td>Free&nbsp;Shipping</td>";
                    else
                        result_html += "<td>$" + item.shippingInfo[0].shippingServiceCost[0].__value__ + "</td>";
                }
                else 
                    result_html += "<td>N/A</td>";
            }
            result_html += "</table>";
            document.getElementById("search_result").innerHTML += result_html;
        }
        
        //-------------------------------------------------------------------------for search details
        function search_detail(itemId){
            console.log("item id:" + itemId);
            var xmlhttp=new XMLHttpRequest(); 
            xmlhttp.open("POST", document.forms[0].action, false);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("itemId="+itemId);
            //console.log(xmlhttp.responseText);
            try {
                var detailJSON = JSON.parse(xmlhttp.responseText);
            }
            catch {
                remove_children("search_result");
                bad_request();
                return;
            }
            console.log(detailJSON);
            show_detail_result(detailJSON);
        }
        
        //-------------------------------------------------------------------------show detail result in table
        function show_detail_result(detailJSON){
            location.href = "#";
            remove_children("search_result");
            if(detailJSON.Ack != "Success"){
                var err = document.createElement("div");
                err.innerHTML = "A ErrorMessage JSON returned! ";
                if(detailJSON.Errors[0].LongMessage) err.innerHTML += "The message is: " + detailJSON.Errors[0].LongMessage;
                err.className = "error";
                document.getElementById("search_result").appendChild(err);
                return;
            }
            var detail_html = "<table id='detail'><caption>Item Details</caption>";
            var item = detailJSON.Item;
            if(item.PictureURL) detail_html += "<tr><th>Photo</th><td><img height='200px' src='"+ item.PictureURL +"'></td></tr>";
            if(item.Title) detail_html +="<tr><th>Title</th><td>"+ item.Title +"</td></tr>";
            if(item.Subtitle) detail_html +="<tr><th>SubTitle</th><td>"+ item.Subtitle +"</td></tr>";
            if(item.CurrentPrice) detail_html +="<tr><th>Price</th><td>"+ item.CurrentPrice.Value + " " + item.CurrentPrice.CurrencyID + "</td></tr>";
            if(item.Location) {
                detail_html +="<tr><th>Location</th><td>" + item.Location;
                if(item.PostalCode) detail_html +=", "+ item.PostalCode;
                detail_html+="</td></tr>";
            }
            if(item.Seller) detail_html +="<tr><th>Seller</th><td>"+ item.Seller.UserID +"</td></tr>";
            if(item.ReturnPolicy) {
                detail_html +="<tr><th>Return&nbsp;Policy(US)</th>";
                if(item.ReturnPolicy.ReturnsAccepted && item.ReturnPolicy.ReturnsAccepted == "Returns Accepted"){
                    detail_html +="<td>"+ item.ReturnPolicy.ReturnsAccepted +" within "+ item.ReturnPolicy.ReturnsWithin +"</td></tr>";
                }
                else {
                    detail_html += "<td>Returns Not Accepted</td></tr>";
                }
            }
            if(item.ItemSpecifics) {
                item_sp_list = item.ItemSpecifics.NameValueList;
                for(var i = 0; i < item_sp_list.length; i++) {
                    item_sp = item_sp_list[i];
                    detail_html +="<tr><th>"+item_sp.Name+"</th><td>";
                    for(var j = 0; j < item_sp.Value.length; j++) {
                        detail_html += item_sp.Value[j] +" ";
                    }
                    detail_html +="</td></tr>";
                }
            }
            else {
                detail_html +="<tr><th>No Detail Info from seller</th><td bgcolor='#ccc'></td></tr>"
            }
            dis = "no";
            load = false;
            if(item.Description)
                dis = item.Description; //global variable for seller message
            else {
                var err = document.createElement("div");
                err.innerHTML = "No Seller Message found";
                err.className = "error";
                document.getElementById("seller").appendChild(err);
            } 
            detail_html += "</table>";
            detail_html += "<p class='click' id='seller_text'>Click to show seller message</p>";
            detail_html += "<img class='arrow' id='arrow_seller' src='http://csci571.com/hw/hw6/images/arrow_down.png' onclick=show_seller()>" ;
            detail_html += "<div id='seller' style='display:none'><iframe id='iframe' width='90%' scrolling='no' frameborder='0'></iframe></div>";
    
            detail_html += "<p class='click' id='similar_text'>Click to show similar items</p>";
            detail_html += "<img class='arrow' id='arrow_similar' src='http://csci571.com/hw/hw6/images/arrow_down.png' onclick=show_similar()>";
            detail_html += "<div id='similar' style='display:none'></div>";
            
            document.getElementById("search_result").innerHTML += detail_html;
            search_similar(item.ItemID);
        }
        
        //-----------------------------------------------------------------two click button show/hidden logic
        function show_seller(){
            if(document.getElementById("seller").style.display == "none") {
                document.getElementById("arrow_seller").src = "http://csci571.com/hw/hw6/images/arrow_up.png";
                document.getElementById("seller").style.display = "";
                document.getElementById("seller_text").innerHTML = "Click to hide seller message";
                if (dis != "no" && !load) {
                    search_seller();
                    load = true;
                    //console.log(Description);
                }
                document.getElementById("arrow_similar").src = "http://csci571.com/hw/hw6/images/arrow_down.png";
                document.getElementById("similar").style.display = "none";
                document.getElementById("similar_text").innerHTML = "Click to show similar items";
            }
            else {
                document.getElementById("arrow_seller").src = "http://csci571.com/hw/hw6/images/arrow_down.png";
                document.getElementById("seller").style.display = "none";
                document.getElementById("seller_text").innerHTML = "Click to show seller message";
            }
        }
        function show_similar(){
            if(document.getElementById("similar").style.display == "none") {
                document.getElementById("arrow_similar").src = "http://csci571.com/hw/hw6/images/arrow_up.png";
                document.getElementById("similar").style.display = "";
                document.getElementById("similar_text").innerHTML = "Click to hide similar items";
                document.getElementById("arrow_seller").src = "http://csci571.com/hw/hw6/images/arrow_down.png";
                document.getElementById("seller").style.display = "none";
                document.getElementById("seller_text").innerHTML = "Click to show seller message";
            }
            else {
                document.getElementById("arrow_similar").src = "http://csci571.com/hw/hw6/images/arrow_down.png";
                document.getElementById("similar").style.display = "none";
                document.getElementById("similar_text").innerHTML = "Click to show seller message";
            }
        }
        
        //-------------------------------------------------------------------put seller html inside iframe
        function search_seller(){
            var myiframe = document.getElementById("iframe");
            //get height of the page
            myiframe.setAttribute('srcdoc', dis);
            myiframe.onload = function(){
                this.height=this.contentWindow.document.documentElement.scrollHeight;
            }
            console.log(myiframe.height);
        }
        
        //---------------------------------------------------------------------------for search similar product
        function search_similar(itemId){
            console.log("item id:" + itemId);
            var xmlhttp=new XMLHttpRequest(); 
            xmlhttp.open("POST", document.forms[0].action, false);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("similar="+itemId);
            //console.log(xmlhttp.responseText);
            try {
                var similarJSON = JSON.parse(xmlhttp.responseText);
            }
            catch {
                var err = document.createElement("div");
                err.innerHTML = "<p>No Similar Item found</p>";
                err.className = "no_similar";
                document.getElementById("similar").appendChild(err);
                return;
            }
            console.log(similarJSON);
            show_similar_result(similarJSON);
        }
        
        //-------------------------------------------------------------------------show similar result in table
        function show_similar_result(similarJSON){
            if(!similarJSON.getSimilarItemsResponse.itemRecommendations.item || similarJSON.getSimilarItemsResponse.itemRecommendations.item.length == 0) {
                var err = document.createElement("div");
                err.innerHTML = "<p>No Similar Item found</p>";
                err.className = "no_similar";
                document.getElementById("similar").appendChild(err);
                return;
            }
            var similar_html = "<div id='similar_div'><table><tr>";
            var items = similarJSON.getSimilarItemsResponse.itemRecommendations.item //array of item
            for(var i = 0; i < items.length; i++) {
                var item = items[i];
                similar_html += "<td>";
                if(item.imageURL) similar_html +="<img src='"+ item.imageURL +"'>";
                if(item.title) similar_html += "<p><a onclick=search_detail(" + item.itemId+ ")>" + item.title +"</a></p>";
                if(item.buyItNowPrice.__value__) similar_html += "<p><b>$" +item.buyItNowPrice.__value__+"</b></p>"; 
                similar_html += "</td>"
            }
            similar_html +="</tr></table></div>";
            document.getElementById("similar").innerHTML += similar_html;
        }
    </script>
    </body>
</html>

<?php 
}
?>
    
    