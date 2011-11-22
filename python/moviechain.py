class Title:
    title = ""
    length = 0
    intarray = []
    edges = []
    searched = 0

fp = open("Movie Puzzle.LST","r")    
wordDict = {}
titleStructArray = []

i=0
n=0
bigarray=[]
print "Generating dictionary"
for title in iter(fp.readline, ''):
    title = title[:-1]
    #n=n+1
    #if(n>=2000):
    #    break
    if title != "":
        newTitle = Title()
        newTitle.title = title
        newTitle.intarray = []
        newTitle.edges = []
        for word in title.split():
            if word not in wordDict:
                wordDict[word] = i
                bigarray += [[]]
                i=i+1
            newTitle.intarray += [wordDict[word]]
            newTitle.length += 1
        titleStructArray += [newTitle]
fp.close()
    
print "Generating big array"
for title in titleStructArray:
    curIndex = title.intarray[0]
    bigarray[curIndex] += [title]

print "Sorting titles by length (descending)"
list.sort(titleStructArray,key=lambda title:title.length,reverse=True)

print "Generating graph edges"
for title in titleStructArray:
    for x in range(title.length-1,-1,-1):
        for nextTitle in bigarray[title.intarray[x]]:
            if nextTitle != title and title.length-x <= nextTitle.length:
                check = True
                for x2 in range(x,title.length):
                    if (x2-x >= nextTitle.length) or (title.intarray[x2] != nextTitle.intarray[x2-x]):
                        check = False
                        break
                if check and (nextTitle not in title.edges):
                    title.edges += [nextTitle]

maxpath = []
maxdepth = 200

def concatPath(path):
    resWords = path[0].split()
    for title in path[1:]:
        words = title.split()
        n=0
        while n < len(words) and words[n] != resWords[-1]:
            n = n+1
        resWords += words[n+1:]
    return " ".join(resWords)

def printPath():
    global maxdepth
    global titleStructArray
    global maxpath
    print
    print "New path of depth",maxdepth
    path = [""]*maxdepth
    for title in titleStructArray:
        if title.searched:
            path[title.searched-1] = title.title
    maxpath = path[:]
    print "->".join(path)
    print "----"
    print "Gives Title:"
    fullTitle = concatPath(path)
    print fullTitle
    print "(%d titles, %d words)"%(len(path),len(fullTitle.split()))


print "Finding longest path:"
def recsearch(title,depth):
    global maxdepth
    title.searched=depth
    if(depth > maxdepth):
        maxdepth = depth
        printPath()
    for edge in title.edges:
        if not edge.searched:
            recsearch(edge,depth+1)
    title.searched=0
            
for (x,title) in enumerate(titleStructArray):
    print x,"titles searched..."
    recsearch(title,1)

print "->".join(maxpath)
resultTitle = concatPath(maxpath)
print resultTitle,len(resultTitle.split())
