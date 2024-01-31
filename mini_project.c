#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define ARRAY_SIZE 10
#define DELAY_MICROSECONDS 1000000 // time taken btw swaps

void delay(int microseconds)
{
    int nanoseconds = microseconds * 1000;
    for (int i = 0; i < nanoseconds; i++)
        ;
    // empty loop to create a delay
}
void swap(int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = temp;

    /**a = *a + *b ;
     *b = *a - *b ;*/
}

void print_array(int arr[], int size, int selected1, int selected2)
{
    for (int i = 0; i < size; i++)
    {
        if (i == selected1 || i == selected2)
        {
            printf("\x1b[34m%d \x1b[0m", arr[i]); // prints text in red
        }
        else
        {
            printf("%d ", arr[i]);
        }
    }
    printf("\r");
    fflush(stdout);
}

void bubblesort(int arr[], int size)
{
    for (int i = 0; i < size - 1; i++)
    {
        for (int j = 0; j < size - i - 1; j++)
        {
            if (arr[j] > arr[j + 1])
            {

                /*without pointers
                int temp=arr[j];
                arr[j]=arr[j+1];
                arr[j+1]=temp;*/

                // with pointers
                swap(&arr[j], &arr[j + 1]); // call by reference
                print_array(arr, ARRAY_SIZE, j, j + 1);
                delay(DELAY_MICROSECONDS);
            }
        }
    }
}

void main()
{
    srand(time(NULL)); // gives current time

    int arr[ARRAY_SIZE];
    for (int i = 0; i < ARRAY_SIZE; i++)
    {
        arr[i] = rand() % 100; // generate random integers between 0 to 100
    }
    printf("Original array: ");
    print_array(arr, ARRAY_SIZE, -1, -1);
    printf("\n");

    printf("Sorting steps:\n");
    bubblesort(arr, ARRAY_SIZE);

    printf("Sorted array: ");
    print_array(arr, ARRAY_SIZE, -1, -1);
    printf("\n");
}