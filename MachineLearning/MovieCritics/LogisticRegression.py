import warnings
import numpy as np

warnings.filterwarnings("ignore")


class LogisticRegression:
    def __init__(self, lr=0.1, num_iter=100, threshold=0.5):
        self.lr = lr
        self.num_iter = num_iter
        self.threshold = threshold


    def __sigmoid(self, z):
        return 1 / (1 + np.exp(-z))

    def __loss(self, h, y):
        return (-y * np.log(h) - (1 - y) * np.log(1 - h)).mean()

    def fit(self, X, y):

        # weights initialization
        self.theta = np.zeros(X.shape[1])

        for i in range(self.num_iter):
            z = np.dot(X, self.theta)
            h = self.__sigmoid(z)
            gradient = np.dot(X.T, (h - y)) / y.size
            self.theta -= self.lr * gradient

            z = np.dot(X, self.theta)
            h = self.__sigmoid(z)
            loss = self.__loss(h, y)

    def predict_prob(self, X):
        return self.__sigmoid(np.dot(X, self.theta))

    def predict(self, X):
        y_pred = self.predict_prob(X)
        for i in range(len(y_pred)):
            if y_pred[i] >= self.threshold:
                y_pred[i] = 1
            else:
                y_pred[i] = 0
        return y_pred
