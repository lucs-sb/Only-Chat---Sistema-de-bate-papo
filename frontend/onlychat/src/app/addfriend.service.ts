import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Friend } from './Friend';

@Injectable({
  providedIn: 'root'
})
export class AddfriendService {

  constructor(private http: HttpClient) { }

  private API_USER_ME: string = `http://localhost:8080/api/user/${localStorage.getItem("userId")}/adicionar`;
  private API_ADD_USER: string = `http://localhost:8080/api/user/${localStorage.getItem("userId")}/contact`;

  findNewsFriend(findString: any): Observable<Friend[]> {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        params: { 'adicionar': findString }
      };

      return this.http.get<Friend[]>(this.API_USER_ME, httpOptions)
    }
  }

  addfriend(id: any): Observable<any> {
    {
      return this.http.post<any>(this.API_ADD_USER, { 'id': id })
    }
  }
}
