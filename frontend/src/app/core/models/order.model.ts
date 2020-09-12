import { CartItem } from './cart-item.model';

export class Order {
    customer : string 
    products : Map<number, CartItem>
}
