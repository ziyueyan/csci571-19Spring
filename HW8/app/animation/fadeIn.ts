import {
    trigger,
    animate,
    transition,
    style
  } from "@angular/animations";

export const FadeAnimation =
  trigger('fadeAnimations', [
    transition("* => *", [
        style({ opacity: 0 }),
        animate(".3s", style({ opacity: 1 }))
    ])
]);