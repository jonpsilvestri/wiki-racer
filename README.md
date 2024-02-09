# wiki-racer
Finds and displays a path between two wikipedia articles using links embedded in Wikipedia articles

A start page and destination page can be given in the command line (2 total commandline arguments)

Output will include the path that was taken between the two pages, along with the total number of pages visited in the search

WikiRacer uses an association heuristic to determine which pages to traverse through. Pages are given priority based on how many embedded links that they share with the target page. Possible paths are stored in a priority queue, where the priority value is calculated based on how many embedded links a page shares with the target

## how to use
Clone this repository, and compile with

```make wikiracer```

Search can be run by providing the names of two Wikipedia arguments.

arg1 -- source page

arg2 -- target page

```java WikiRacer Stanford_university emu```

clear executables with 

```make clean```

![alt text](https://github.com/[jonpsilvestri]/[wikiracer]/blob/[main]/sample_output.png?raw=true)
