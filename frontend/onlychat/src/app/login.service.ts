import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { StorageService } from './storage.service';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private API_USER_ME: string = 'https://web-only-chat.herokuapp.com/api/user/login';

  constructor(private http: HttpClient, private localStorage: StorageService) { }

  login(username: string, password: string) {

    const httpOptions = {
      headers: { authorization: 'Basic ' + btoa(username + ':' + password) },
      params: { 'email': username }
    };

    return this.http.get<any>(this.API_USER_ME, httpOptions).pipe(
      tap(response => {
        this.localStorage.set('userId', response.id);
        this.localStorage.set('userEmail', response.email);
        this.localStorage.set('userName', response.name);
        this.localStorage.set('userGender', response.gender);
        if (response.photo == null)
          this.localStorage.set('userUrl_photo', response.url_photo);
        else
          this.localStorage.set('userPhoto', response.photo);

        return response
      })
    );
  }

  logout() {
    this.localStorage.clear()
  }
}
