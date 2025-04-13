
import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import {UserDashboardComponent} from './components/user-dashboard/user-dashboard.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';

// export const routes: Routes = [
//   { path: '', component: LoginComponent },
//   { path: 'login', component: LoginComponent },
//   { path: 'user-dashboard', component: UserDashboardComponent },
//   { path: 'user-dashboard', component: UserDashboardComponent }
// ];

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent },
  { path: 'user-dashboard', component: UserDashboardComponent },
];


