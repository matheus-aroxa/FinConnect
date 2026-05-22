import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'app' },
	{
		path: 'auth',
		loadChildren: () =>
			import('./features/auth/auth.module').then((module) => module.AuthModule),
	},
	{
		path: 'app',
		canActivate: [authGuard],
		loadChildren: () =>
			import('./features/dashboard/dashboard.module').then(
				(module) => module.DashboardModule
			),
	},
	{ path: '**', redirectTo: 'app' },
];
