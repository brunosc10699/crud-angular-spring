import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  transform(value: string): string {
    switch(value) {
      case 'Frontend Development': return 'code';
      case 'Backend Development': return 'computer';
    }
    return 'code';
  }

}
