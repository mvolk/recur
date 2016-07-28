# recur
Aggregates recurring transactions into monthly budget numbers.

This simple utility is motivated by my personal budgeting process. I compile a list of
recurring transactions and use that list as the starting point for budgeting. In the
past I have manually transformed my list of recurring expenses into monthly budget
figures. Aside from being rote and error-prone, manual compilation also reduces the
value of my data for modeling purposes. This utility is the answer to those simple
problems.

Light on test coverage, so the usual "use at your own risk" caution has extra weight
on this project.

[![Build Status](https://travis-ci.org/mvolk/recur.svg?branch=master)](https://travis-ci.org/mvolk/recur)

## Build

    ./gradlew clean build

## Run

For example, to run a budget for the year 2017 with recurring transaction defined in data.csv:

    ./gradlew run -PappArgs="['2017','path/to/data/data.csv']"

## Data format

I define my data in Google Sheets and export as CSV. Column headers must be defined in the first
row, and are case sensitive. The schema is as follows:

| Column Header Value | Format     | Description |
| ------------------- | ---------- | ----------- |
| Description         | Text       | A description of the recurring transaction. While a value is not required in any row, the column must be present. |
| Amount              | Decimal    | The amount of each transaction in precision of your choosing. Required. |
| Start               | yyyy-MM-dd | The date of the first transaction. Required. |
| End                 | yyyy-MM-dd | The date of the last transaction. While a value is not required in any row, the column must be present. |
| Period              | integer    | The number of units (below) between transactions. Required. |
| Units               | Choice     | One of 'Days', 'Weeks', 'Months' or 'Years'. Required. |
| Category            | Text       | The budget category into which this transaction falls. Required. |
| Enabled             | Choice     | Indicates whether to ignore the transaction. Ignored if not 'TRUE' without respect to case. Required. |
