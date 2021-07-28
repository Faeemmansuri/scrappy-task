import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MultiSelectDropdownComponent } from 'src/components/dropdown-multiselect/dropdown.component';
import { FilterComponent } from 'src/components/filter/filter.component';
import { PaginationComponent } from 'src/components/pagination/pagination.component';
import { TableComponent } from 'src/components/table/table.component';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    MultiSelectDropdownComponent,
    TableComponent,
    FilterComponent,
    PaginationComponent,
  ],
  imports: [BrowserModule, FormsModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
