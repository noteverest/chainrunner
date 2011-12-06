// Placeholder

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

// basic string word counter
int num_words (string s) {
  int count = 0;
  for (int i = 0; i < s.length(); ++i) {
    if (s[i] == ' ') {
      ++count;
    }
  }
  return ++count;
}

// Title node class
class Title {
  public:
    // index in the big array
    int index;
    // number of words
    int len;
    // array of words (to simplify comparison)
    vector<string> wordlist;
    // each Title tracks adjacent edges by index
    vector<int> edges;
    // to memoize longest path
    int longest_path;

    // prints info
    void DEBUG () {
      cout << "i=" << index
           << " len=" << len
           << " lp=" << longest_path
           << " t=";
      for (int a = 0; a < len; ++a) {
        cout << wordlist[a] << ' ';
      }
      cout << '\n'
           << "e= [";
      for (int b = 0; b < edges.size(); ++b) {
        cout << edges[b] << ',';
      }
      cout << "]\n";
      return;
    }

    // Constructor
    Title (string t, int i) {
      index = i;
      //longest_path init -1 b/c unchecked
      longest_path = -1;
      len = num_words (t);
      // Filling the title_array
      int index = 0;
      int word_start = 0;
      for (int c = 0; c < t.length(); ++c) {
        if (t[c] == ' ') {
          // DEBUG: print each word
          //cout << '(' << word_start << ' ' << c - word_start << ") ." <<
          //  t.substr (word_start, c - word_start) << ". ";
          wordlist.push_back (t.substr (word_start, c - word_start));
          word_start = c+1;
          index++;
        }
      }
      wordlist.push_back (t.substr (word_start, t.length() - word_start));
    // DEBUG: print last word 
    //cout << '(' << word_start << ' ' << t.length() - word_start << ") ." <<
    //  t.substr (word_start, t.length() - word_start) << ".\n";
    }
};

// compare the tail of t_word to the head of h_word
// return true if overlap, else false
bool compare_titles (vector<string> t_word, vector<string> h_word) {
  
  int t_len = t_word.size();
  int h_len = h_word.size();
  int min_len = min(t_len, h_len);

  // m = wordlist modifier
  // offset from tail of t_word and head of h_word
  for (int m = 0; m < min_len; ++m) {
    if (t_word[t_len - 1 - m] == h_word[0]) {
      //DEBUG
      //cout << "Match: " << t_word[t_len - 1 - m] << ' ' << h_word[0] << '\n';
      bool match_fail = false;
      // if we get a match, introduce a new modifier
      //  re-iterate through the overlap
      for (int mx = 0; mx <= m; ++mx) {
        if (t_word[t_len - 1 - m + mx] != h_word[mx]) {
          match_fail = true;
        }
      }
      if (match_fail == false) {
        // DEBUG: print the match
        for (int ti = 0; ti < t_len; ++ti) {cout << t_word[ti] << ' ';}
        cout << "=> ";
        for (int hi = 0; hi < h_len; ++hi) {cout << h_word[hi] << ' ';}
        cout << '\n';
        // end DEBUG
        return true;
      }
    }
  }
  return false;
}

void get_edges (vector<Title> list, int i) {
  // li = list index
  for (int li = 0; li < list.size(); ++li) {
    if (i != li) {
      if (compare_titles (list[i].wordlist, list[li].wordlist)) {
        // push any successful comparisons 
        list[i].edges.push_back (li); 
      }
    }
  }

  // if we found no edges for t, it's a dead end
  if (list[i].edges.empty() == true) {
    list[i].longest_path = 0;
  }
}


// Main function
int main () {

  // load movielist
  ifstream movielist ("movies.lst");

  // temporary title info
  string new_title;
  int movie_number = 0;

  // initialize titlelist
  vector<Title> big_titlelist;
 
  // readlines
  if (movielist.is_open())
  {
    while ( movielist.good() )
    {
      getline (movielist, new_title);
      Title temp (new_title, movie_number);
      //DEBUG: tests that titles are initializing properly
      //temp.DEBUG();
      big_titlelist.push_back (temp);
      ++movie_number;
    }
    movielist.close();
    cout << "List loaded! \n";
  }
  else cout << "Unable to open file";

  for (int i = 0; i < big_titlelist.size(); ++i) {
    // DEBUG: prints edge matches
    cout << "Getting edges for title " << i << '\n';
    get_edges (big_titlelist, i);
  }

  return 0;
}
