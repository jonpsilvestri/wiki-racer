# wiki-racer
A java project that will find a path from one Wikipedia article to the other using only links embedded in the article
A start page and destination page can be given in the command line (2 total commandline arguments)

Output will include the path that was taken between the two pages, along with the total number of pages visited in the search

WikiRacer uses an association heuristic to determine which pages to traverse through. Any given traversal ladder will have a priority associated with it, which is calculated by seeing how many links the current page (last link in the ladder) has in common with the target page. These ladders are added to a Max Priority Queue, and the ladders with the most links in common with the target will be explored first. If a link in the ladder contains the link target page, the target page is added to the ladder, and the search is terminated
