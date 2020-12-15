	
.data
	entradaA: .float 1			# entradaA = 1
	entradaB: .float 1			# entradaB = 1
	pesoA: .float 0.0			# pesoA = 0.0
	pesoB: .float 0.8			# pesoB = 0.8
	saidaDesejada: .float 2.0		# saidaDesejada = 2
	taxaAprendizagem: .float 0.05		# taxaAprendizagem = 0.05
	saidaNeuronio: .float 0			# saidaNeuronio = 0
	erro: .float 0				# erro = 0
	incremento1: .float 1			# contador++
	incremento2: .float 2			# contador+2
	entradaTeste: .float 1			# entradaTeste = 1
	numero: .word 1
	
arrayTeste:	.space 40                	# reserva espaco de 40 bytes (10 posicoes de 4 bytes)
mensagem: 	.asciiz "Vamos testar se o neurônio aprendeu após uma época: \n"
soma: 		.asciiz " + "
igual: 		.asciiz " = "
nextLine: 	.asciiz "\n"
	
.text
 	lwc1 $f1, entradaA			# entradaA = $f1
	lwc1 $f3, entradaB			# entradaB = $f3
	lwc1 $f5, pesoA				# pesoA = $f5
	lwc1 $f7, pesoB				# pesoB = $f7
	lwc1 $f9, saidaDesejada			# saidaDesejada = $f9
	lwc1 $f11, taxaAprendizagem		# taxaAprendizagem = $f11
	lwc1 $f13, saidaNeuronio		# saidaNeuronio = $f13
	lwc1 $f15, erro		 		# erro = $f15
	lwc1 $f17, incremento1			# incremento1 = $f17
	lwc1 $f19, incremento2			# incremento2 = $f19
	lw $s3, numero				# numero = $s3
	
	
	
# TREINAMENTO DO NEURONIO
				
	add $t0, $0, $0 			# int i = 0 para o for
	addi $s0, $0, 5 			# colocar 5 como o numero de iteracoes		
for:
	slt $t1, $t0, $s0			# se i < 5, entao $t1 = 1
	beq $t1, $0, teste			# se $t1 == 0, ir para o teste

	# calcular a saidaNeuronio
	mul.s $f21, $f1, $f5			# $f21 = entradaA * pesoA
	mul.s $f23, $f3, $f7			# $f23 = entradaB * pesoB
	add.s $f13, $f21, $f23			# saidaNeuronio = $f21 + $f23	

	# calcular o erro
	sub.s $f15, $f9, $f13			# erro = saidaDesejada - saidaNeuronio

	# ajustar o peso A
	mul.s $f25, $f11, $f15			# $f25 = taxaAprendizagem * erro
	mul.s $f25, $f25, $f1			# $f25 = $f10 * entradaA
	add.s $f5, $f5, $f25			# pesoA = pesoA + $f25

	# ajustar o peso B
	mul.s $f27, $f11, $f15			# $f27 = taxaAprendizagem * erro
	mul.s $f27, $f27, $f3			# $f27 = $f27 * entradaB
	add.s $f7, $f7, $f27			# pesoB = pesoB + $f27

	# acrescentar 2 na saidaDesejada e 1 nas entradas A e B, a cada iteracao 
	add.s $f9, $f9, $f19			# saidaDesejada +2
	add.s $f1, $f1, $f17			# entradaA ++
	add.s $f3, $f3, $f17			# entradaB ++

	# retorno ao for
	addi $t0, $t0, 1 			# i++
	j for					# ir para o for



# TESTE DO NEURONIO
				
	# imprime mensagem
teste:	addi $v0, $0, 4
	la $a0, mensagem
	syscall
	
	# arrayTeste          
  	add $t0, $0, $0 			# i=0
  	lwc1 $f29, entradaTeste		 	# entradaTeste = 1.0
  loop: 
  	mul $t3, $t0, 4				# deslocamento = i * 4
  	lw $t4, arrayTeste($t3)			# arrayTeste[i] = incremento de 4
  	mul.s $f20, $f29, $f5			# entradaTeste * pesoA
  	mul.s $f22, $f29, $f7			# entradaTeste * pesoB
  	add.s $f24, $f20, $f22			# soma os valores das entradas * pesos
  	swc1  $f24, arrayTeste($t0)		# salva o resultado em Array[n]
  	
  	#imprimir os testes	
  	addi $v0, $s3, 0
  	add $a0, $0, $v0
  	addi $v0, $0, 1
  	syscall					# imprime o valor de entrada para teste
  	
  	addi $v0, $0, 4
  	la $a0 soma
  	syscall					# umprime o '+'
  	
  	addi $v0, $s3, 0
  	add $a0, $0, $v0
  	addi $v0, $0, 1
  	syscall					# imprime o outro valor de entrada para teste
  	addi $s3, $s3, 1			# faz numero++
  	
  	addi $v0, $0, 4
  	la $a0 igual
  	syscall					# imprime o '='
  	
  	li $v0, 2
  	mov.s $f12, $f24
  	syscall					# imprime o resultado do teste usando os pesos obtidos no treinamento
  	
  	addi $v0, $0, 4
  	la $a0 nextLine
  	syscall					# pula pra próxima linha
  	
  	# incremento nos contadores e retorno ao loop
	addi  $t0, $t0, 4 			# faz n++
	add.s $f29, $f29, $f17			# faz entradaTeste++
  	blt   $t0, 40, loop 			# salta para o inicio do loop se $t0 < 40

fim:	
	jr $ra 					# return
  
  
  
# CÓDIGO EM JAVA:
		
#static void Main(string[] args)         
#{             
#	int entradaA = 1;
#	int entradaB = 1;
#	double pesoA = 0.0;
#	double pesoB = 0.8;
#	double saidaDesejada = 2;
#	double taxaAprendizagem = 0.05;
#	double saidaNeuronio = 0;
#	double erro = 0;

		 		 
# TREINAMENTO DO NEURONIO		
#		for(int i = 0; i < 5 ; i++) {
#			saidaNeuronio = (entradaA * pesoA) + (entradaB * pesoB);
#			erro = saidaDesejada - saidaNeuronio;
#			pesoA = pesoA + (taxaAprendizagem * erro * entradaA);
#			pesoB = pesoB + (taxaAprendizagem * erro * entradaB);
#			
#			saidaDesejada = saidaDesejada + 2;
#			entradaA ++;
#			entradaB ++;			
#		}

# TESTE DO NEURONIO
#		System.out.println("Vamos testar se o neurônio aprendeu após uma época:");
#		float[] arrayTeste = new float[11];
#		int entradaTeste = 1;
#		float saidaNeuronioTeste = 0;
#		int n = 0;
#
#		do {
#			saidaNeuronioTeste = (entradaTeste * (float) pesoA) + (entradaTeste * (float) pesoB);
#			arrayTeste[n] = entradaTeste;
#			arrayTeste[n + 1] = saidaNeuronioTeste;
#			System.out.printf("%d + %d = %.2f\n", entradaTeste, entradaTeste, arrayTeste[n + 1]);
#			entradaTeste++;
#			n++;
#
#		} while (n < 10);



