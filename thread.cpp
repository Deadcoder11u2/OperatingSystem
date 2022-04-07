#include <bits/stdc++.h>
using namespace std;
using namespace std::chrono;
mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());
int randint(int l, int r) {return uniform_int_distribution<int>(l, r)(rng);}
vector<int> result ;
void findLargest(vector<int> v , int l , int r , int idx)
{
int ans = INT_MIN;
for (int i = l ; i <= r ; i++)
{
ans = max(ans , v[i]) ;
}
result[idx] = ans ;
}
int main()
{
int n = 10000000,i,noofthreads;
vector<int> v(n);
for (i=0;i<n;i++)
v[i] = randint(1,INT_MAX);
cin >> noofthreads;
int width = n / noofthreads;
if (n % noofthreads != 0)
noofthreads++;
vector<thread> parallelism(noofthreads);
result.resize(noofthreads);
int l,r;
auto start = high_resolution_clock::now();
for (i=0;i<noofthreads;i++)
{
l = i * width ;
r = min(l + width - 1 , n - 1) ;
parallelism[i] = thread(findLargest, v , l , r , i) ;
}
for (i=0;i<noofthreads;i++)
parallelism[i].join() ;
int finalans = INT_MIN;
for (i=0;i<noofthreads;i++)
finalans = max(finalans , result[i]) ;
cout <<"The largest number is :"<<finalans<<endl ;
auto stop = high_resolution_clock::now();
auto duration = duration_cast<milliseconds>(stop - start);
cout<< duration.count() << " ms" ;
}
