import { Pipe, PipeTransform } from '@angular/core';
import { DayPipePipe } from './day-pipe.pipe';

@Pipe({
  name: 'orderPipe'
})
export class OrderPipePipe implements PipeTransform {

  transform(value: Array<any>, key?: any, order?: any) : any{
    if(key === "Default"){
      return (order === "Ascending") ?  value : value.reverse();
    }
    if(key === "Product Name") {
      return value.sort((item1: any, item2: any) => {
        return order === "Ascending" ? this.compare(false, item1.title, item2.title) : this.compare(true, item1.title, item2.title);
      });
    }
    if(key === "Days Left") {
      return value.sort((item1: any, item2: any) => {
        return order === "Ascending" ? this.compare(false, new DayPipePipe().transform(item1.timeLeft),  new DayPipePipe().transform(item2.timeLeft)) : this.compare(true,  new DayPipePipe().transform(item1.timeLeft), new DayPipePipe().transform(item2.timeLeft));
      });
    }
    if(key === "Price") {
      return value.sort((item1: any, item2: any) => {
        return order === "Ascending" ? this.compare(false, parseFloat(item1.buyItNowPrice.__value__),  parseFloat(item2.buyItNowPrice.__value__)) : this.compare(true,  parseFloat(item1.buyItNowPrice.__value__),  parseFloat(item2.buyItNowPrice.__value__));
      });
    }
    if(key === "Shipping Cost") {
      return value.sort((item1: any, item2: any) => {
        return order === "Ascending" ? this.compare(false,  parseFloat(item1.shippingCost.__value__),  parseFloat(item2.shippingCost.__value__)) : this.compare(true,  parseFloat(item1.shippingCost.__value__),  parseFloat(item2.shippingCost.__value__));
      });
    }
  }

  compare (reverse: boolean, a: any, b: any): number {
    if (a < b && reverse === false) {
        return -1;
    }
    if (a > b && reverse === false) {
        return 1;
    }
    if (a < b && reverse === true) {
        return 1;
    }
    if (a > b && reverse === true) {
        return -1;
    }
    return 0;
}
}
