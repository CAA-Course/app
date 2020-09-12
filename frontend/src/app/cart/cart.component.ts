import { Component, OnInit } from '@angular/core';
import { CartService } from '../core/services/cart.service';
import { CartItem } from '../core/models/cart-item.model';
import { OrderService } from '../core/services/order.service';
import { Order } from '../core/models/order.model';
import { AuthenticationService } from '../core/services/authentication.service';
import * as $ from 'jquery';
import 'bootstrap-notify';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent implements OnInit {
  cartItems: Map<number, CartItem>

  constructor(private cartService: CartService,
    private authenticationService: AuthenticationService,
    private orderService: OrderService) { }

  ngOnInit() {
    this.cartItems = this.cartService.getCartItems()
  }

  incrementQuantity(cartItem: CartItem) {
    this.cartService.incrementCartItem(cartItem);
  }

  decrementQuantity(cartItem: CartItem) {
    this.cartService.decrementCartItem(cartItem);
  }

  checkout() {
    if (this.cartService.isEmpty() == true) {
      let customer: string = this.authenticationService.currentUser.username
      let products = this.cartItems
      let order: Order = { customer, products }
      this.orderService.createOrder(order)
        .subscribe(
          data => {
            this.cartService.clearCart();
            $[`notify`]({
              icon: "fa fa-info-circle",
              title: "Order created!",
              message: "You have successfully created an order!",
            }, { type: "success", delay: 1000 });
          },
          error => {
            $[`notify`]({
              icon: "fa fa-info-circle",
              title: "Order creation failed!",
              message: "Order could not be created!",
            }, { type: "danger", delay: 1000 });
          });
    }
    else {
      $[`notify`]({
        icon: "fa fa-info-circle",
        title: "Order creation failed!",
        message: "You don't have any products in your cart!",
      }, { type: "danger", delay: 1000 },
      );
    }
  }

}
