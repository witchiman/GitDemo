from tkinter import *
root = Tk()

text=Text(root,width =30,height=5)
text.pack()

text.insert(INSERT,'i love the world')

def getIndex(text,index):
    return tuple(map(int,str.split(text.index(index),'.')))

start = '1.0'
while True:
    pos = text.search('o',start,stopindex = END)
    if not pos:
        break
    print('It\'s here',getIndex(text,pos))
    start = pos + '+1c'

mainloop()
