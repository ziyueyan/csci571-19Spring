const express = require('express');
const http = require('http');
const https = require('https');
const path = require('path');
const app = express();
const port = 8081;

app.use(express.static(__dirname + '/dist/HW8'));

app.get('/*', (req, res) => res.sendFile(path.join(__dirname)));

const server = http.createServer(app);

server.listen(port, () => console.log('Running...'));

app.get('/', function(req,res) {
    res.status(200).send("hello from server");
});

app.get('/search', function(req, res) {
    console.log(req.url);
    console.log(req.query);
    var appid = "ZiyueYan-ZYproduc-PRD-816db7c91-02b29d47";
    var eBay_URL = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=" + appid + "&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=50";
    eBay_URL += "&keywords=" + encodeURI(req.query.keyword);
    if(req.query.category != null && req.query.category != "-1"){
        eBay_URL += "&categoryId=" + req.query.category;
    }
    var count = 0;
    if(req.query.zipcode != ''){
        eBay_URL += "&buyerPostalCode=" + req.query.zipcode;
        eBay_URL += "&itemFilter(" + count + ").name=MaxDistance&itemFilter(" + count + ").value=" + encodeURI(req.query.miles);
        count++;
    }
    if(req.query.localpickup == 'true') {
        eBay_URL += "&itemFilter(" + count + ").name=LocalPickupOnly&itemFilter(" + count + ").value=true";
        count++;
    }
    if(req.query.freeshipping == 'true') {
        eBay_URL += "&itemFilter(" + count + ").name=FreeShippingOnly&itemFilter(" + count + ").value=true";
        count++;
    }
    eBay_URL += "&itemFilter("+ count +").name=HideDuplicateItems&itemFilter("+ count +").value=true";
    count++;
    if(req.query.new == 'true' || req.query.used == 'true' || req.query.unspecified == 'true') {
        eBay_URL += "&itemFilter(" + count + ").name=Condition";
        var con_count = 0;
        if(req.query.new == 'true') {
            eBay_URL += "&itemFilter(" + count + ").value(" + con_count + ")=New";
            con_count++;
        }
        if(req.query.used == 'true') {
            eBay_URL += "&itemFilter(" + count + ").value(" + con_count + ")=Used";
            con_count++;
        }
        if(req.query.unspecified == 'true') {
            eBay_URL += "&itemFilter(" + count + ").value(" + con_count + ")=Unspecified";
        }
    }
    eBay_URL += "&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo";
    console.log(eBay_URL);
    http.get(eBay_URL, (resp) => {
        let data = '';
        resp.on('data', (chunk) => {
        data += chunk;
        });
        resp.on('end', () => {
        var resultJSON = JSON.parse(data);  
        //console.log(resultJSON);
        if(resultJSON.findItemsAdvancedResponse){
            if(resultJSON.findItemsAdvancedResponse[0].ack[0] == "Success"){
                result="{\"items\":[";
                item = resultJSON.findItemsAdvancedResponse[0].searchResult[0].item;
                if(resultJSON.findItemsAdvancedResponse[0].searchResult[0]["@count"] != "0"){
                    for(i = 0; i < item.length; i++){
                        result += "{\"image\":" ;
                        if(item[i].galleryURL) result += "\""+ item[i].galleryURL[0] + "\",";
                        else result += "\"N/A\",";
                        result += "\"title\":" ;
                        if(item[i].title) result += "\"" +item[i].title[0].replace(/\"/g, '') + "\",";
                        else result += "\"N/A\",";
                        result += "\"zipcode\":" ;
                        if(item[i].postalCode) result += "\"" + item[i].postalCode[0] + "\",";
                        else result += "\"N/A\",";
                        result += "\"shippingcost\":" ;
                        if(item[i].shippingInfo && item[i].shippingInfo[0].shippingServiceCost) {
                            if(item[i].shippingInfo[0].shippingServiceCost[0].__value__ == '0.0')
                                result += "\"" + "Free Shipping" + "\",";
                            else
                                result += "\"$" + item[i].shippingInfo[0].shippingServiceCost[0].__value__ + "\",";
                        }
                        else result += "\"N/A\",";
                        result += "\"handling\":" ;
                        if(item[i].shippingInfo && item[i].shippingInfo[0].handlingTime) result += "\"" + item[i].shippingInfo[0].handlingTime[0]+ "\",";
                        else result += "\"N/A\",";
                        result += "\"condition\":" ;
                        if(item[i].condition && item[i].condition[0].conditionDisplayName) result += "\"" + item[i].condition[0].conditionDisplayName[0]+ "\",";
                        else result += "\"N/A\",";
                        result += "\"price\":" ;
                        if(item[i].sellingStatus && item[i].sellingStatus[0].currentPrice) result += "\"$" + item[i].sellingStatus[0].currentPrice[0].__value__+ "\",";
                        else result += "\"N/A\",";
                        result += "\"itemId\":" ;
                        if(item[i].itemId) result += "\"" + item[i].itemId[0] + "\"";
                        else result += "\"N/A\"";
                        result += "}";
                        if(i != item.length-1){
                            result +=",";
                        }
                    }
                }
                result+="]}";
                console.log(JSON.parse(result));
                res.status(200).send(JSON.parse(result));
                //res.status(200).send(resultJSON);
            }
            else {
                res.status(401).send({"Error Message":resultJSON.findItemsAdvancedResponse[0].errorMessage[0].error[0].message[0]});
            }
        }
        else {
            res.status(401).send({"Error Message":"error"});
        }
        });
    }).on("error", (err) => {
        console.log("Error: " + err.message);
        res.status(401).send({"Error Message": "somthing wrong"});
    });
});

app.get('/detail', function(req, res) {
    console.log(req.url);
    console.log(req.query);
    var appid = "ZiyueYan-ZYproduc-PRD-816db7c91-02b29d47";
    var detail_URL = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=" + appid 
                    + "&siteid=0&version=967&ItemID=" + req.query.itemId
                    + "&IncludeSelector=Description,Details,ItemSpecifics";
    console.log(detail_URL);
    http.get(detail_URL, (resp) => {
        let data = '';
        resp.on('data', (chunk) => {
        data += chunk;
        });
        resp.on('end', () => {
        var detailJSON = JSON.parse(data);  
        item = detailJSON.Item;
        //console.log(detailJSON);
        if(detailJSON.Ack == "Success"){
            result="{";
            result += "\"itemId\":" ;
            if(item.ItemID) result += "\"" + item.ItemID + "\",";
            else result += "\"N/A\",";
            result += "\"picture\":" ;
            if(item.PictureURL) {
                result += "["
                for(i = 0; i < item.PictureURL.length; i++) {
                    result += "\"" + item.PictureURL[i] + "\"";
                    if(i != item.PictureURL.length-1){
                        result += ",";
                    }
                }
                result += "],"
            }  
            else result += "[],";
            result += "\"price\":" ;
            if(item.CurrentPrice) result += "\"$" + item.CurrentPrice.Value+ "\",";
            else result += "\"N/A\",";
            result += "\"subtitle\":" ;
            if(item.Subtitle) result += "\"" + item.Subtitle+ "\",";
            else result += "\"N/A\",";
            var brand = "";
            result += "\"spec\":" ;
            if(item.ItemSpecifics) {
                result += "["
                for(i = 0; i < item.ItemSpecifics.NameValueList.length; i++) {
                    if(item.ItemSpecifics.NameValueList[i].Name == "Brand"){
                        brand = item.ItemSpecifics.NameValueList[i].Value[0].replace(/\"/g, '');
                    }
                    result += "\"· " +item.ItemSpecifics.NameValueList[i].Value[0].replace(/\"/g, '')+"\"";
                    if(i != item.ItemSpecifics.NameValueList.length-1){
                        result += ",";
                    }
                }
                result += "],"
            }
            else result += "[],";

            result += "\"store\":" ;
            if(item.Seller.UserID) result += "\"" + item.Seller.UserID + "\",";
            else result += "\"N/A\",";
            result += "\"storeurl\":" ;
            if(item.Storefront && item.Storefront.StoreURL) result += "\"" + item.Storefront.StoreURL + "\",";
            else result += "\"N/A\",";
            result += "\"star\":" ;
            if(item.Seller.FeedbackRatingStar) result += "\"" + item.Seller.FeedbackRatingStar + "\",";
            else result += "\"N/A\",";
            result += "\"score\":" ;
            if(item.Seller.FeedbackScore) result += "\"" + item.Seller.FeedbackScore + "\",";
            else result += "\"N/A\",";
            result += "\"popular\":" ;
            if(item.Seller.PositiveFeedbackPercent) result += "\"" + item.Seller.PositiveFeedbackPercent + "\",";
            else result += "\"N/A\",";

            result += "\"global\":" ;
            if(item.GlobalShipping) result += "\"" + item.GlobalShipping + "\",";
            else result += "\"N/A\",";
            result += "\"condition\":" ;
            if(item.ConditionDescription) result += "\"" + item.ConditionDescription + "\",";
            else result += "\"N/A\",";
            
            result += "\"policy\":" ;
            if(item.ReturnPolicy.ReturnsAccepted) result += "\"" + item.ReturnPolicy.ReturnsAccepted + "\",";
            else result += "\"N/A\",";
            result += "\"within\":" ;
            if(item.ReturnPolicy.ReturnsWithin) result += "\"" + item.ReturnPolicy.ReturnsWithin + "\",";
            else result += "\"N/A\",";
            result += "\"mode\":" ;
            if(item.ReturnPolicy.Refund) result += "\"" + item.ReturnPolicy.Refund + "\",";
            else result += "\"N/A\",";
            result += "\"shipby\":" ;
            if(item.ReturnPolicy.ShippingCostPaidBy) result += "\"" + item.ReturnPolicy.ShippingCostPaidBy + "\",";
            else result += "\"N/A\",";

            result += "\"brand\":" ;
            if(brand !== "") result += "\"" + brand.replace(/\"/g, '') + "\"";
            else result += "\"N/A\"";
            result+="}";
            //add more
            console.log(JSON.parse(result));
            res.status(200).send(JSON.parse(result));
        }
        else {
            res.status(401).send({"Error Message":"error returned"});
        }
        });
    }).on("error", (err) => {
        console.log("Error: " + err.message);
        res.status(401).send({"Error Message": "somthing wrong with detail search"});
    });
});

app.get('/photo', function(req, res) {
    console.log(req.url);
    var key = "AIzaSyCfBJVb1PiKeGBjV-UYW1fhRmR1RijfxfU";
    var cx = '008969835453167643334:qpk6wwqda2q';
    var google_URL = "https://www.googleapis.com/customsearch/v1?q=" 
                    + encodeURI(req.query.q)
                    +"&cx="+ cx + "&imgSize=huge&imgType=news&num=8&searchType=image&key="+key;
    console.log(google_URL);
    https.get(google_URL, (resp) => {
    let data = '';
    resp.on('data', (chunk) => {
        data += chunk;
    });
    resp.on('end', () => {
        var photoJSON = JSON.parse(data);  
        //console.log(photoJSON);
        result="{";
        result += "\"photo\":" ;
        if(photoJSON.searchInformation.totalResults !== '0'){
            result += "["
            for(i = 0; i < photoJSON.items.length; i++) {
                result += "\"" + photoJSON.items[i].link +"\"";
                if(i != photoJSON.items.length-1){
                    result += ",";
                }
            }
            result += "]"
        }
        else {
            result += "[]";
        }
        result += "}"
        console.log(JSON.parse(result));
        res.status(200).send(JSON.parse(result));
    });
    }).on("error", (err) => {
    console.log("Error: " + err.message);
    res.status(401).send({"Error Message": "somthing wrong with photo search"});
    });
});


app.get('/similar', function(req, res) {
    console.log(req.url);
    console.log(req.query);
    var appid = "ZiyueYan-ZYproduc-PRD-816db7c91-02b29d47";
    var similar_URL = "http://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID=" 
                    + appid + "&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId=" + req.query.itemId + "&maxResults=20";
    console.log(similar_URL);
    http.get(similar_URL, (resp) => {
        let data = '';
        resp.on('data', (chunk) => {
            data += chunk;
        });
        resp.on('end', () => {
            var similarJSON = JSON.parse(data);  
            //console.log(similarJSON);
            if(similarJSON.getSimilarItemsResponse.ack == "Success"){
                result="{\"similar\":" ;
                if(similarJSON.getSimilarItemsResponse.itemRecommendations.item.length !== '0'){
                    item = similarJSON.getSimilarItemsResponse.itemRecommendations.item;
                    result += "["
                    for(i = 0; i < item.length; i++) {
                        result += "{\"image\":" ;
                        if(item[i].imageURL) result += "\""+ item[i].imageURL + "\",";
                        else result += "\"N/A\",";
                        result += "\"url\":" ;
                        if(item[i].viewItemURL) result += "\"" +item[i].viewItemURL + "\",";
                        else result += "\"N/A\",";
                        result += "\"title\":" ;
                        if(item[i].title) result += "\"" +item[i].title.replace(/\"/g, '') + "\",";
                        else result += "\"N/A\",";
                        result += "\"shippingcost\":" ;
                        if(item[i].shippingCost) {
                            if(item[i].shippingCost.__value__ == '0.00')
                                result += "\"" + "Free Shipping" + "\",";
                            else
                                result += "\"$" + item[i].shippingCost.__value__ + "\",";
                        }
                        else result += "\"N/A\",";
                        result += "\"price\":" ;
                        if(item[i].buyItNowPrice) result += "\"$" + item[i].buyItNowPrice.__value__ + "\",";
                        else result += "\"N/A\",";
                        result += "\"time\":" ;
                        if(item[i].timeLeft) result += "\"" + item[i].timeLeft + "\"}";
                        else result += "\"N/A\"}";
                        if(i != item.length-1){
                            result += ",";
                        }
                    }
                    result += "]"
                }
                else {
                    result += "[]";
                }
                result += "}"
                console.log(JSON.parse(result));
                res.status(200).send(JSON.parse(result));
            }
            else {
            res.status(401).send({"Error Message":"error returned"});
            }
        });
        }).on("error", (err) => {
        console.log("Error: " + err.message);
        res.status(401).send({"Error Message": "somthing wrong with similar search"});
    });
});


app.get('/zip', function(req, res) {
    console.log(req.url);
console.log(req.query);
var zip_URL = "http://api.geonames.org/postalCodeSearchJSON?postalcode_startsWith="+ req.query.zip+"&username=huyue&country=US&maxRows=5";
console.log(zip_URL);
http.get(zip_URL, (resp) => {
    let data = '';
    resp.on('data', (chunk) => {
        data += chunk;
    });
    resp.on('end', () => {
        var zipJSON = JSON.parse(data);  
        
        var ziparray = [];
        for(var i = 0; i < zipJSON.postalCodes.length; i++) {
        ziparray[i] = zipJSON.postalCodes[i].postalCode;
        }
        //if(zipJSON.getSimilarItemsResponse.ack == "Success"){
        //console.log("similar success");
        console.log(ziparray);
        res.status(200).send(ziparray);
        // }
        //else {
        //res.status(401).send({"Error Message":"error returned"});
        //}
    });
    }).on("error", (err) => {
    console.log("Error: " + err.message);
    res.status(401).send({"Error Message": "somthing wrong with zip search"});
});
});
