import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'stringPipe'
})
export class StringPipePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    args = args||35;

    return value && value.length>35?value.substr(0,args)+'...':value;;
  }

}
