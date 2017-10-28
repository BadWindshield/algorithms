

Check my Java's version:
$ javac -version
javac 1.8.0_131


Download the textbook libraries from algs4.jar and the Java wrapper scripts from javac-algs4, javac-cos226, javac-coursera, java-algs4, java-cos226, and java-coursera.
$ mkdir algs4
$ cd algs4/
$ curl -O "https://algs4.cs.princeton.edu/code/algs4.jar"
$ curl -O "https://algs4.cs.princeton.edu/linux/{javac,java}-{algs4,cos226,coursera}"
$ sudo mv {javac,java}-{algs4,cos226,coursera} /usr/local/bin

$ sudo mkdir /usr/local/algs4
$ sudo mv algs4.jar /usr/local/algs4


Compile and run the "Hello World" test program.

public class HelloWorld {
    public static void main(String[] args) { 
        System.out.println("Hello, World");
    }
}

$ javac HelloWorld.java
$ java HelloWorld



Oct 28, 2017
============
Set up a git repo on Caliper.

1. Log in as user "git" and add the ssh public key.
$ sudo su - git

$ cat xx.pub >> ~/ssh/authorized_keys


2. Set up the repo.
$ cd git
$ mkdir algorithms.git
$ cd algorithms.git
$ git init --bare


3. Set up user name and email.
In .git/config, add

[user]
        name = William Wong
        email = william.s.wong@gmail.com

Check by running
$ git config --list


4.  As "williamw",
$ git init
$ git add README.txt
$ git commit

// Check that the correct credentials are used.
$ gitk

$ git remote add origin git@10.0.1.13:/home/git/git/algorithms.git
$ git push origin master


5. To clone the repo.

  $ git clone git@localhost:/home/git/git/algorithms.git

