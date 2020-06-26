# Timelines

[![Clojars Project](https://img.shields.io/clojars/v/uk.rogerthemoose/timelines.svg)](https://clojars.org/uk.rogerthemoose/timelines)

for rendering events on a series of timelines

#### Development

*to run a simple test harness*

`clojure -A:fig -b dev -r`

*to run the tests*

`clojure -A:test`

#### Elements

* wrap svg objects with coordinates defined by line number (y-axis) and date (x-axis)
* are building blocks (timeline, point, arrow etc.) 
* e.g. a _timeline_ element on some line may run from 2020-06-01 to 2020-08-01 and is then rendered as a line of length 61 which is the number of days between the start and end date 
* each element renders to one or more SVG objects
* users of this library will likely map their domain objects into groups of these more basic elements to visualise some business flow

#### Layout 

* provides simple layout of the elements onto a grid defined in terms of lines and dates
* lines are numbered from 1.. and run top to bottom
* days run left to right
* the canvas size is calculated determined according to the elements added to the drawing



