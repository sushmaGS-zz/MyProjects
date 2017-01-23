var app = angular.module("calci", []);
app.controller("calciCtrl", calciCtrl);

function calciCtrl(){
	this.resultValue = 0;
	this.buttonClicked = function(button){
		this.selectedOperation = button;
	}

	this.computeResult = function(){
		var num1 = parseFloat(this.input1);
		var num2 = parseFloat(this.input2);

		if (this.selectedOperation === '+') {
			this.resultValue = num1+num2;
		}
		if (this.selectedOperation === '-') {
			this.resultValue = num1-num2;
		}
		if (this.selectedOperation === '*') {
			this.resultValue = num1*num2;
		}
		if (this.selectedOperation === '/') {
			this.resultValue = num1/num2;
		}
		if (this.selectedOperation === '%') {
			this.resultValue = num1%num2;
		}
	}


}
