import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { StorageService } from '../storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string = localStorage.getItem("userName")!;

  constructor(private loginService: LoginService) {

  }

  ngOnInit(): void {
  }

  logout() {
    this.loginService.logout()
  }

}
