import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dayPipe'
})
export class DayPipePipe implements PipeTransform {

  transform(value: string, args?: any): any {
    var day = 0;
    var i = 1;
    while(value.charAt(i) != 'D') {
      day = day * 10;
      day += Number(value.charAt(i));
      i++;
    }
    return day;
  }

}
