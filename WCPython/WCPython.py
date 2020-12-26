wordstring = 'it was the best of times it was the worst of times '
wordstring += 'it was the age of wisdom it was the age of foolishness'

wordlist = wordstring.split()
print(wordlist)

wordfreq = []
for w in wordlist:
    wordfreq.append(wordlist.count(w))

print("String\n",wordstring,"\n")
from collections import defaultdict

d = defaultdict(list)

for i in wordlist:
    d[i].append(1)

print(d)

pair_list = [{wordlist[i]:1 for i in range(len(wordlist))}]
pairs = {wordlist[i]:wordfreq[i] for i in range(len(wordlist))}

print(pair_list)

print("\nTotal words\n", len(wordstring))
