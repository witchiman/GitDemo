from tkinter import *

master = Tk()

sb=Scrollbar(master)
sb.pack(side = RIGHT,fill=Y)

theLB = Listbox(master,selectmode = EXTENDED,yscrollcommand = sb.set)
theLB.pack()

for item in range(199):
    theLB.insert(END,item)
theLB.pack(side=LEFT,fill =BOTH)
sb.config(command=theLB.yview)

theButton = Button(master,text = '删除它',\
                   command= lambda x= theLB:x.delete(ACTIVE))
theButton.pack(anchor = E)

mainloop()
