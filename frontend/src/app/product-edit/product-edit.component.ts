import { Component, OnInit } from '@angular/core';
import { Product } from '../core/models/product.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from '../store/app.states';
import { Observable } from 'rxjs';
import { GetProduct, UpdateProduct } from '../store/actions/product.actions';
import { getSelectedProduct } from '../store/selectors/product.selectors';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html'
})
export class ProductEditComponent implements OnInit {
  product$: Observable<Product>;
  actualProduct: Product;
  id: number;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private store: Store<AppState>) {
    this.product$ = this.store.select(getSelectedProduct);
  }

  ngOnInit() {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.store.dispatch(new GetProduct(this.id));
    this.product$.subscribe((product: Product) => this.actualProduct = product);
  }

  saveProduct(product: Product) {
    this.store.dispatch(new UpdateProduct(product));
    this.router.navigate(['..']);
  }

}
