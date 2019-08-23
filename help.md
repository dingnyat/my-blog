##Hướng dẫn deploy Heroku
1 Nếu chưa có git trong project thì init git
>git init

>git add *

>git commit -m "a"

2 Nếu có git rồi, kể cả git từ trước ko mục đích deploy thì thôi
3 tạo app

>heroku create [app name]

4 push len heroku

>git push heroku master

5 sau mỗi lần update thì add lại rồi commit rồi push lên
