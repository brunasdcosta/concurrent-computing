from random import choice
from string import ascii_lowercase, digits
import os

file = open('dataset12.txt', 'w')
words = set()
while(True):
    word = ''.join(choice(ascii_lowercase + digits) for _ in range(12))
    if word not in words:
        words.add(word)
        file.write(f'{word}\n')    
    if os.path.getsize('dataset12.txt')>=1000000000:
        break
file.close()