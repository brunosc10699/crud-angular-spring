import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private httpClient: HttpClient) { }

  list(): Course[] {
    return [
        { _id: '1', name: 'Angular', category: 'Frontend Development'},
        { _id: '2', name: 'Java', category: 'Backend Development'},
        { _id: '3', name: 'NodeJS', category: 'Backend Development'}
      ];
  }
}
