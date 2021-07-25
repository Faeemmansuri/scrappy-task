import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { TableComponent } from '../components/table/table.component';
import { FilterComponent } from '../components/filter/filter.component';
import { MultiSelectDropdownComponent } from '../components/dropdown-multiselect/dropdown.component';
import { PaginationComponent } from '../components/pagination/pagination.component';

@NgModule({
  imports: [BrowserModule, FormsModule, HttpClientModule],
  declarations: [
    AppComponent,
    MultiSelectDropdownComponent,
    TableComponent,
    FilterComponent,
    PaginationComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
