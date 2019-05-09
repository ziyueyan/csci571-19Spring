import {
    trigger,
    state,
    animate,
    transition,
    style,
    query,
    animateChild,
    group
  } from "@angular/animations";

export const slideAnimation =
    trigger('routeAnimations', [
        state('detail', style({
            transform: 'translateX(100%)',
            opacity: '1',
            overflow: 'hidden',
            display: 'none'
        })),
        state('list', style({
            opacity: '1',
            display: 'block'
        })),
        transition('list => detail', animate('400ms ease-in')),
        //transition('detail => list', animate('400ms ease-out'))
    ]);
    /*
    state('detail', style({
        transform: 'translateX(100%)',
        opacity: '1',
        overflow: 'hidden',
        display: 'none'
    })),
    state('list', style({
        opacity: '1',
        display: 'block'
    })),
    transition('list => detail', animate('400ms ease-out')),
    transition('detail => list', animate('400ms ease-in'))
    
    transition('wishPage <=> resultPage', [
        style({ position: 'relative' }),
        query(':enter, :leave', [
            style({
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%'
            })
        ], { optional: true }),
        query(':enter', [
            style({ left: '-100%'})
        ], { optional: true }),
        query(':leave', animateChild(), { optional: true }),
        group([
            query(':leave', [
            animate('600ms ease-out', style({ left: '100%'}))
            ], { optional: true }),
            query(':enter', [
            animate('600ms ease-out', style({ left: '0%'}))
            ], { optional: true })
        ]),
        query(':enter', animateChild(), { optional: true }),
        ]),
      transition('wishPage <=> detailPage', [
        style({ position: 'relative' }),
        query(':enter, :leave', [
            style({
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%'
            })
        ], { optional: true }),
        query(':enter', [
            style({ left: '-100%'})
        ], { optional: true }),
        query(':leave', animateChild(), { optional: true }),
        group([
            query(':leave', [
            animate('600ms ease-out', style({ left: '100%'}))
            ], { optional: true }),
            query(':enter', [
            animate('600ms ease-out', style({ left: '0%'}))
            ], { optional: true })
        ]),
        query(':enter', animateChild(), { optional: true }),
    ]),*/
