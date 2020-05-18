mkdir git
cd git
git init
git config --global user.name ChaSang-geol
git config --global user.email hunipapa@nate.com

echo test > test.java
git add test.java
git status

git commit -m "first commit"

git remote add origin https://github.com/ChaSang-geol/edu-repository.git
git push -u origin master

git pull
git log
