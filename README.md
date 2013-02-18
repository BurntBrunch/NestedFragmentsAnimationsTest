NestedFragmentsAnimationsTest
=============================

A small testcase for a bug with animating nested fragments in Android

On the first screen, the backgound of the container is RED, the nested
fragments are MAGENTA and BLACK. Clicking anywhere on the screen will add a
fragment with a RANDOM colour. Observe that while that fragment is entering the
screen, the nested fragments disappear and the RED background flashes.
