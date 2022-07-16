import { User } from "./user";

export interface Contact{
    id: number;
    principal: User;
    friend: User;
    date_time: string;
}