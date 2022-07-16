import { Contact } from "../app/contact";

export interface Message {
  id: Number;
  message: String;
  date_time: String;
  contact_id: Contact;
}