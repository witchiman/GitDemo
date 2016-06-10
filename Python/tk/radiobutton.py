from tkinter import*

root=Tk()
group = LabelFrame(root,text = '最好的脚本语言是?',padx = 5,pady=5)
group.pack(padx =10,pady=12)
languages=[('python',1),
           ('perl',2),
           ('lua',3)]
v= IntVar()
v.set(2)
for language,num in languages:
    b= Radiobutton(group,text=language,variable= v,value=num)
    b.pack(anchor =W)

mainloop()
