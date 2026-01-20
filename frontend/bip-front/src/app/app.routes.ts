import { Routes } from '@angular/router';
import { Beneficio } from './negocio/beneficio/beneficio';

export const routes: Routes = [
    { path: 'beneficio-home', component: Beneficio },
    { path: '', redirectTo: '/beneficio-home', pathMatch: 'full' }
];
