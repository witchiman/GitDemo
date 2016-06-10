
from tkinter import *

root = Tk()

frame1= Frame(root)
frame2 = Frame(root)

def callback():
    var.set('我才不怕那')
var=  StringVar()
var.set('请远离危险，\n珍爱生命')
textLabel = Label(frame1,textvariable = var,justify=LEFT,
                  padx =10,fg='white',bg='brown')
textLabel.pack()

photo = PhotoImage(file = '1.gif')
imgLabel = Label(root,image=photo)
imgLabel.pack(side = RIGHT)

theButton = Button(frame2,text='我不怕',command = callback)
theButton.pack()

frame1.pack(padx =10,pady=10)
frame2.pack(padx=10,pady=10)

root.mainloop()
