#include <boost/filesystem.hpp>   
#include <iostream>
using namespace std;
int main(int ac, char** av)
{
   string extension;
   int count = 0;
   boost::filesystem::recursive_directory_iterator iterator(string("/mnt/d/Prog/Prog2Konyv/source/MasodikFejezet/Stroustrup/src"));
   while(iterator != boost::filesystem::recursive_directory_iterator())
   {
      string extension = boost::filesystem::extension(iterator->path().filename());
         if( boost::filesystem::is_regular_file(iterator->path()) && extension == ".java"){
            count++;
         }
      ++iterator;   
   }
   cout << count << endl;
   return 0;
}
