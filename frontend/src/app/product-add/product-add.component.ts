import { Component } from '@angular/core';
import { Product } from '../core/models/product.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from '../store/app.states';
import { CreateProduct } from '../store/actions/product.actions';

@Component({
  selector: 'app-product-add',
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent {

  product: Product = new Product();

  constructor(private route: ActivatedRoute,
    private router: Router,
    private store: Store<AppState>) { }

  saveProduct(product: Product) {
    this.store.dispatch(new CreateProduct(product));
    this.router.navigate(['..']);
  }

}
