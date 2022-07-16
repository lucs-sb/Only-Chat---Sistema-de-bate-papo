import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private API_USER_ME: string = 'https://web-only-chat.herokuapp.com/api/user';

  constructor(private http: HttpClient) { }

  register(email: string, name: string, gender: string, password: string, url_photo: string) {
    const body = {
      email: email,
      name: name,
      gender: gender,
      password: password,
      url_photo: url_photo
    };

    return this.http.post<Object>(this.API_USER_ME, body);
  }
}
