#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>

void jelkezelo(){
    printf("anyad\n");
}

int main(){
    
    while(1) {
      if(signal(SIGINT, jelkezelo)==SIG_IGN) 
        signal(SIGINT, jelkezelo);
   }
   return(0);

}

