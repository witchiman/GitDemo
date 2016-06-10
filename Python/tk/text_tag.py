from tkinter import*

root = Tk()

text= Text(root,width = 20,height= 5)
text.pack()

text.insert(INSERT,'i love the world!')

text.tag_add('tag1','1.7','1.10','1.13')
text.tag_config('tag1',background='yellow',foreground='purple')

mainloop()


