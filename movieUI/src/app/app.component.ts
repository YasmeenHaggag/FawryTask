// import { Component } from '@angular/core';
// import { RouterOutlet } from '@angular/router';
// import { bootstrapApplication } from '@angular/platform-browser';
// // import { AppComponent } from './app/app.component';
// import { provideRouter } from '@angular/router';
// import { routes } from './app.routes';
//
// @Component({
//   selector: 'app-root',
//   imports: [RouterOutlet],
//   templateUrl: './app.component.html',
//   standalone: true,
//   styleUrl: './app.component.css'
// })
// export class AppComponent {
//   title = 'movieUI';
// }
//
//
// bootstrapApplication(AppComponent, {
//   providers: [provideRouter(routes)]
// });


import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'movieUI';
}

