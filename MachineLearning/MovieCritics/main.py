import tensorflow as tf
import warnings
from DecisionTreeClassifier import DecisionTree
import time
import matplotlib.pyplot as plt
import numpy as np
from random_forest import RandomForest
from LogisticRegression import LogisticRegression

warnings.filterwarnings("ignore")


def vectorize(sequences, dimension=1000):
    results = np.zeros((len(sequences), dimension))
    for i, sequence in enumerate(sequences):
        results[i, sequence] = 1
    return results


def accuracy(y_true, y_pred):
    TP = counterFunction(y_true, y_pred, 1)
    TN = counterFunction(y_true, y_pred, 4)
    accuracy = (TP + TN) / len(y_true)
    return accuracy


def counterFunction(y_true, y_pred, operation):
    counter = 0
    if operation == 1:  # True Positives
        for i in range(len(y_true)):
            if y_pred[i] == y_true[i] and y_pred[i] == 1:
                counter += 1
    elif operation == 2:  # False Positives
        for i in range(len(y_true)):
            if y_pred[i] != y_true[i] and y_pred[i] == 1:
                counter += 1
    elif operation == 3:  # False Negatives
        for i in range(len(y_true)):
            if y_pred[i] != y_true[i] and y_pred[i] == 0:
                counter += 1
    elif operation == 4:  # True Negatives
        for i in range(len(y_true)):
            if y_pred[i] == y_true[i] and y_pred[i] == 0:
                counter += 1
    return counter


def precision(y_true, y_pred):
    TP = counterFunction(y_true, y_pred, 1)
    FP = counterFunction(y_true, y_pred, 2)
    c1 = TP / (TP + FP)  # precision Positive
    TN = counterFunction(y_true, y_pred, 4)
    FN = counterFunction(y_true, y_pred, 3)
    c2 = TN / (TN + FN)  # precision Negative
    return (c1 + c2) / 2


def recall(y_true, y_pred):
    TP = counterFunction(y_true, y_pred, 1)
    FP = counterFunction(y_true, y_pred, 2)
    TN = counterFunction(y_true, y_pred, 4)
    FN = counterFunction(y_true, y_pred, 3)
    c1 = TP / (TP + FN)  # recall Positive
    c2 = TN / (TN + FP)  # recall Negative
    return (c1 + c2) / 2


def f1(y_true, y_pred):
    return (2 * precision(y_true, y_pred) * recall(y_true, y_pred)) / \
           (precision(y_true, y_pred) + recall(y_true, y_pred))


def runID3(numword, skiptop, maxdepth, operation=0):
    (x_train, y_train), (x_test, y_test) = tf.keras.datasets.imdb.load_data(  # x  = kritiki y = pos/neg
        path="imdb.npz",
        num_words=numword,
        skip_top=skiptop,
        maxlen=None,
        seed=113,
        start_char=1,
        oov_char=2,
        index_from=3,
    )
    train_accs = list()
    dev_accs = list()
    test_accs = list()
    prec = list()
    rec = list()
    f_1 = list()
    data = np.concatenate((x_train, x_test), axis=0)
    targets = np.concatenate((y_train, y_test), axis=0)
    data = vectorize(data, dimension=numword)
    targets = np.array(targets).astype("int64")
    if operation == 1:
        for i in range(1, 11):
            test_x = data[:25000]
            test_y = targets[:25000]
            dev_x = data[47500:]
            dev_y = targets[47500:]
            p = round(22500 * (i / 10))
            end_train = 25000 + p
            train_x = data[25000:end_train]
            train_y = targets[25000:end_train]
            clf = DecisionTree(max_depth=maxdepth)
            clf.fit(train_x, train_y)
            y_pred = clf.predict(dev_x)
            y_pred_train = clf.predict(train_x)
            y_pred_test = clf.predict(test_x)
            acc_test = accuracy(test_y, y_pred_test)
            acc_dev = accuracy(dev_y, y_pred)
            acc_train = accuracy(train_y, y_pred_train)
            print("Accuracy:", acc_dev)
            print("Accuracy in train set", acc_train)
            test_accs.append(acc_test)
            train_accs.append(acc_train)
            dev_accs.append(acc_dev)
            if acc_dev == max(dev_accs):
                end_train_saver = end_train
        max_dev_pos = dev_accs.index(max(dev_accs))
        print("Highest Test Accuracy for the best dev point: ", test_accs[max_dev_pos])
        plotfunction(train_accs, dev_accs, "TRAIN", "DEV")
        train_x = data[25000:end_train_saver]
        train_y = targets[25000:end_train_saver]
        test_x = data[:25000]
        test_y = targets[:25000]
        for i in range(1, 10):
            threshold = i / 10
            clf = DecisionTree(max_depth=maxdepth, threshold=threshold)
            clf.fit(train_x, train_y)
            y_pred_test = clf.predict(test_x)
            prec.append(precision(test_y, y_pred_test))
            rec.append(recall(test_y, y_pred_test))
            f_1.append(f1(test_y, y_pred_test))
        plotfunction(prec, rec, "PRECISION", "RECALL", 1)
        print("Precision: ", prec)
        print("Recall: ", rec)
        print("F1: ", f_1)
    else:
        test_x = data[:25000]
        test_y = targets[:25000]
        train_x = data[25000:47500]
        train_y = targets[25000:47500]
        clf = DecisionTree(max_depth=maxdepth)
        clf.fit(train_x, train_y)
        y_pred = clf.predict(test_x)
        acc = accuracy(test_y, y_pred)
        print("Accuracy:", acc)
    return


def runRandomForest(numword, skiptop, maxdepth, trees, operation=0):
    (x_train, y_train), (x_test, y_test) = tf.keras.datasets.imdb.load_data(  # x  = kritiki y = pos/neg
        path="imdb.npz",
        num_words=numword,
        skip_top=skiptop,
        maxlen=None,
        seed=113,
        start_char=1,
        oov_char=2,
        index_from=3,
    )
    train_accs = list()
    dev_accs = list()
    test_accs = list()
    prec = list()
    rec = list()
    f_1 = list()
    data = np.concatenate((x_train, x_test), axis=0)
    targets = np.concatenate((y_train, y_test), axis=0)
    data = vectorize(data, dimension=numword)
    targets = np.array(targets).astype("int64")
    if operation == 1:
        for i in range(1, 11):
            test_x = data[:25000]
            test_y = targets[:25000]
            dev_x = data[47500:]
            dev_y = targets[47500:]
            p = round(22500 * (i / 10))
            end_train = 25000 + p
            train_x = data[25000:end_train]
            train_y = targets[25000:end_train]
            clf = RandomForest(n_trees=trees, max_depth=maxdepth)
            clf.fit(train_x, train_y)
            y_pred = clf.predict(dev_x)
            y_pred_train = clf.predict(train_x)
            y_pred_test = clf.predict(test_x)
            acc_test = accuracy(test_y, y_pred_test)
            acc_dev = accuracy(dev_y, y_pred)
            acc_train = accuracy(train_y, y_pred_train)
            print("Accuracy:", acc_dev)
            print("Accuracy in train set", acc_train)
            test_accs.append(acc_test)
            train_accs.append(acc_train)
            dev_accs.append(acc_dev)
            if acc_dev == max(dev_accs):
                end_train_saver = end_train
        max_dev_pos = dev_accs.index(max(dev_accs))
        print("Highest Test Accuracy for the best dev point: ", test_accs[max_dev_pos])
        plotfunction(train_accs, dev_accs, "TRAIN", "DEV")
        train_x = data[25000:end_train_saver]
        train_y = targets[25000:end_train_saver]
        test_x = data[:25000]
        test_y = targets[:25000]
        for i in range(1, 10):
            threshold = i / 10
            clf = RandomForest(n_trees=trees, max_depth=maxdepth, threshold=threshold)
            clf.fit(train_x, train_y)
            y_pred_test = clf.predict(test_x)
            prec.append(precision(test_y, y_pred_test))
            rec.append(recall(test_y, y_pred_test))
            f_1.append(f1(test_y, y_pred_test))
        plotfunction(prec, rec, "PRECISION", "RECALL", 1)
        print("Precision: ", prec)
        print("Recall: ", rec)
        print("F1: ", f_1)
    else:
        test_x = data[:25000]
        test_y = targets[:25000]
        train_x = data[25000:]
        train_y = targets[25000:]
        clf = RandomForest(n_trees=trees, max_depth=maxdepth)
        clf.fit(train_x, train_y)
        y_pred = clf.predict(test_x)
        acc = accuracy(test_y, y_pred)
        print("Accuracy:", acc)
    return


def runLogisticRegression(numword, skiptop, numiter, learning, operation=0):
    (x_train, y_train), (x_test, y_test) = tf.keras.datasets.imdb.load_data(  # x  = kritiki y = pos/neg
        path="imdb.npz",
        num_words=numword,
        skip_top=skiptop,
        maxlen=None,
        seed=113,
        start_char=1,
        oov_char=2,
        index_from=3,
    )
    train_accs = list()
    dev_accs = list()
    test_accs = list()
    prec = list()
    rec = list()
    f_1 = list()
    data = np.concatenate((x_train, x_test), axis=0)
    targets = np.concatenate((y_train, y_test), axis=0)
    data = vectorize(data, dimension=numword)
    targets = np.array(targets).astype("int64")
    if operation == 1:
        for i in range(1, 11):
            test_x = data[:25000]
            test_y = targets[:25000]
            dev_x = data[47500:]
            dev_y = targets[47500:]
            p = round(22500 * (i / 10))
            end_train = 25000 + p
            train_x = data[25000:end_train]
            train_y = targets[25000:end_train]
            clf = LogisticRegression(num_iter=numiter, lr=learning)
            clf.fit(train_x, train_y)
            y_pred = clf.predict(dev_x)
            y_pred_train = clf.predict(train_x)
            y_pred_test = clf.predict(test_x)
            acc_test = accuracy(test_y, y_pred_test)
            acc_dev = accuracy(dev_y, y_pred)
            acc_train = accuracy(train_y, y_pred_train)
            print("Accuracy:", acc_dev)
            print("Accuracy in train set", acc_train)
            test_accs.append(acc_test)
            train_accs.append(acc_train)
            dev_accs.append(acc_dev)
            if acc_dev == max(dev_accs):
                end_train_saver = end_train
        max_dev_pos = dev_accs.index(max(dev_accs))
        print("Highest Test Accuracy for the best dev point: ", test_accs[max_dev_pos])
        plotfunction(train_accs, dev_accs, "TRAIN", "DEV")
        train_x = data[25000:end_train_saver]
        train_y = targets[25000:end_train_saver]
        test_x = data[:25000]
        test_y = targets[:25000]
        for i in range(1, 10):
            threshold = i / 10
            clf = LogisticRegression(num_iter=numiter, lr=learning, threshold=threshold)
            clf.fit(train_x, train_y)
            y_pred_test = clf.predict(test_x)
            prec.append(precision(test_y, y_pred_test))
            rec.append(recall(test_y, y_pred_test))
            f_1.append(f1(test_y, y_pred_test))
        plotfunction(prec, rec, "PRECISION", "RECALL", 1)
        print("Precision: ", prec)
        print("Recall: ", rec)
        print("F1: ", f_1)
    else:
        test_x = data[:25000]
        test_y = targets[:25000]
        train_x = data[25000:]
        train_y = targets[25000:]
        clf = LogisticRegression(num_iter=numiter, lr=learning)
        clf.fit(train_x, train_y)
        y_pred = clf.predict(test_x)
        acc = accuracy(test_y, y_pred)
        print("Accuracy:", acc)
    return


def plotfunction(train_accs, dev_accs, str1, str2, operation=0):
    if operation == 0:
        plt.rcParams['figure.figsize'] = [15, 10]
        ax = plt.subplot(111)
        t1 = np.arange(0.0, 1.0, 0.01)
        plt.plot(list(range(len(train_accs))), train_accs, '--', label=str1)
        plt.plot(list(range(len(dev_accs))), dev_accs, label=str2)
        leg = plt.legend(loc='lower center', ncol=2, mode="expand", shadow=True, fancybox=True)
        leg.get_frame().set_alpha(0.5)
        plt.show()

        ax = plt.subplot(111)
        t1 = np.arange(0.0, 1.0, 0.01)
        plt.plot(list(range(len(train_accs))), train_accs, '--', label=str1)
        plt.plot(list(range(len(dev_accs))), dev_accs, label=str2)
        leg = plt.legend(loc='lower center', ncol=2, mode="expand", shadow=True, fancybox=True)
        leg.get_frame().set_alpha(0.5)
        plt.ylim(0.0, 1.1)
        plt.show()
    else:
        x = dev_accs
        y = train_accs
        plt.plot(x, y)
        plt.xlabel(str1)
        plt.ylabel(str2)
        plt.show()


if __name__ == '__main__':
    checker = True
    while checker:
        answer = input("Please type the number that you want: 1.Run an algorithm once 2.Create TRAIN - DEV and "
                       "PRECISION - RECALL plot "
                       "\n Here: ")
        if answer == "2":
            start = time.time()
            print("Session Timer Started Now")
            answer = input("Please type the number that you want: 1. ID3 2. Random Forest 3. Logistic Regression"
                           "\n Here: ")
            if answer == "1":
                answer1 = int(input("Type How many words should we include?"
                                    "\n Here: "))
                answer2 = int(input("Type How many words should we skip?"
                                    "\n Here: "))
                answer3 = int(input("Type What is the max depth that you want?"
                                    "\n Here: "))
                runID3(answer1, answer2, answer3, 1)
            elif answer == "2":
                answer1 = int(input("Type How many words should we include?"
                                    "\n Here: "))
                answer2 = int(input("Type How many words should we skip?"
                                    "\n Here: "))
                answer3 = int(input("Type What is the max depth that you want?"
                                    "\n Here: "))
                answer4 = int(input("How many trees do you want?"
                                    "\n Here: "))
                runRandomForest(answer1, answer2, answer3, answer4, 1)
            else:
                answer1 = int(input("Type How many words should we include?"
                                    "\n Here: "))
                answer2 = int(input("Type How many words should we skip?"
                                    "\n Here: "))
                answer3 = int(input("Type How many iterations should we have?"
                                    "\n Here: "))
                answer4 = float(input("Type What learning rate should we have?"
                                      "\n Here: "))
                runLogisticRegression(answer1, answer2, answer3, answer4, 1)
            end = time.time()
            print("Timer Ended Now")
            timeEl = round((end - start) / 60)
            print("Time Elapsed : ", timeEl, "Minutes")
            answer = input("Do you want to leave? :  ")
            if answer == "Yes":
                checker = False

        elif answer == "1":
            start = time.time()
            print("Session Timer Started Now")
            answer = input("Please type the number that you want: 1. ID3 2. Random Forest 3. Logistic Regression"
                           "\n Here: ")
            if answer == "1":
                answer1 = int(input("Type How many words should we include?"
                                    "\n Here: "))
                answer2 = int(input("Type How many words should we skip?"
                                    "\n Here: "))
                answer3 = int(input("Type What is the max depth that you want?"
                                    "\n Here: "))
                runID3(answer1, answer2, answer3)
            elif answer == "2":
                answer1 = int(input("Type How many words should we include?"
                                    "\n Here: "))
                answer2 = int(input("Type How many words should we skip?"
                                    "\n Here: "))
                answer3 = int(input("Type What is the max depth that you want?"
                                    "\n Here: "))
                answer4 = int(input("How many trees do you want?"
                                    "\n Here: "))
                runRandomForest(answer1, answer2, answer3, answer4)
            else:
                answer1 = int(input("Type How many words should we include?"
                                    "\n Here: "))
                answer2 = int(input("Type How many words should we skip?"
                                    "\n Here: "))
                answer3 = int(input("Type How many iterations should we have?"
                                    "\n Here: "))
                answer4 = float(input("Type What learning rate should we have?"
                                      "\n Here: "))
                runLogisticRegression(answer1, answer2, answer3, answer4)
            end = time.time()
            print("Timer Ended Now")
            timeEl = round((end - start) / 60)
            print("Time Elapsed : ", timeEl, "Minutes")
            answer = input("Do you want to leave? :  ")
            if answer == "Yes":
                checker = False
    print("Thank you for your time!")
