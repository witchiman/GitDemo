from tkinter import *
import hashlib
root =Tk()

text = Text(root,width =30,height =5)
text.pack()

text.insert(INSERT,'i love the world!')
contents = text.get('1.0',END)

def getSig(contents):
            m = hashlib.md5(contents.encode())
            return m.digest()
sig = getSig(contents)

def check():
    contents = text.get('1.0',END)
    if sig != getSig(contents):
            print('warning:the contents han changed!')
    else:
            print('everything is okay!')

Button(root,text = '检查',command = check).pack()

mainloop()
