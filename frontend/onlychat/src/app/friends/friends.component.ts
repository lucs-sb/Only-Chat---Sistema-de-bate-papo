import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../friends.service';
import { NotifierService } from 'angular-notifier';
import { StorageService } from '../storage.service';
import { User } from '../user';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  private notifier: NotifierService;
  removeFriendId = 0;
  friends: User[] = [];
  findName: string = '';

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private friendsService: FriendsService, notifier: NotifierService, private localStorage: StorageService) {
    this.notifier = notifier;
    this.getFriends();
  }

  ngOnInit(): void {
    this.getFriends();
  }

  getFriends(): void {
    this.friendsService.getFriends().subscribe((friends) => (this.friends = friends));
  }

  setRemoveId(event: { target: any; srcElement: any; currentTarget: any; }) {
    var target = event.target || event.srcElement || event.currentTarget;
    this.removeFriendId = target.attributes.id.value;
  }

  setEmailToChat(event: { target: any; srcElement: any; currentTarget: any; }) {
    var target = event.target || event.srcElement || event.currentTarget;
    this.localStorage.set('EmailToChat', target.attributes.id.value.toString());
  }

  sendRemoveFriend() {
    this.friendsService.sendRemoveFriend(this.removeFriendId).subscribe(res => {
      this.notifier.notify('success', 'Operação realizada com sucesso');

      this.friends = this.friends.filter(item => item.id != this.removeFriendId);
    })
  }

  findFriends() {
    this.friends = [];
    this.friendsService.findFriends(this.findName).subscribe((friends) => (this.friends = friends));
  }

}
