Baixando o ROBOCODE: https://github.com/robo-code/robocode/releases
Por onde começar?: https://liag.ft.unicamp.br/robocode/robocode/por-onde-comecar/

=============================================================================
Trabalho : Robocode 

Esse trabalho representa 100% da nota do Semestre , ele consiste em utilizar o programa Robocode 
para criar robôs virtuais que batalham em uma arena , a linguagem de utilizada é JAVA para programar os robôs.

serão duas provas , então cada grupo opta por criar dois robôs ou um único para competir nas duas modalidades :

- um Arena ( o robô contra todos os outros da sala ) 
- 1 X 1 ( um contra um )
===============================================================================

UNIVERSIDADE PAULISTA UNIP

ROBOCODE – ROBOT “SENTUFUMO”

Aluno:
Raul de Caires Ferreira


Novembro de 2012
São Paulo

Robot “Sentufumo”

Basicamente o robô Sentufumo trabalha seguindo o inimigo. Através do método onScannedRobot, que é executado toda vez em que o nosso radar detecta um inimigo, são recolhidos dados do inimigo (distancia com relação ao nosso robô, ângulo, velocidade, etc) e com base nesses dados o robô executa os comandos pré-estabelecidos. A técnica de IA adotada para o robô é a de Árvore de Decisão. Foi adotada nos aspectos de movimentação e artilharia do robô.
Movimentação – A movimentação do Robô é diferente em determinadas situações. Quando se encontra a uma distancia D1 (a partir de 250) do inimigo, a ideia é de aproximação até o alvo até a distancia D2 (entre 100 e 250). Ao chegar a uma distância D2 do inimigo, a intenção passa a ser atingir a distância D3 (menor que 100). Ao chegar a uma distancia D3, o robô passa a girar seu eixo com a finalidade de permanecer numa direção perpendicular à direção inimiga e, também se movimentar para trás ao mesmo tempo, permitindo que o robô gire em volta do inimigo. Além disso, o Sentufumo, ao perceber que se encontra a uma distancia inferior a 10 das extremidades da arena, faz o movimento “setBack” de forma inversa ao que estava fazendo, afim de evitar a colisão com a parede.
Artilharia – Nesse aspecto, primeiro tem-se o fator tiro. O tipo de tiro a ser utilizado, é definido de acordo com a situação momentânea. Caso o robô se encontre a uma distancia D1 do inimigo, é utilizado o tiro de tipo 1, pois gasta menos energia, evitando assim maiores prejuízos no caso de erro, visto que a uma distancia grande do inimigo a probabilidade de acerto é inferior. Caso esteja a uma distancia D2, é utilizado um tiro de potencia 2 (este causa mais dano, porém é mais lento que o de força 1) pois nessa distancia a probabilidade de acerto aumenta. Caso esteja a uma distância D3, é utilizado o tiro de força máxima 3, com o intuito de causar mais danos no inimigo.  Além disso, foi feito um cálculo para o caso de o inimigo ter pouca energia. Caso o inimigo se encontre com menos de 16 de energia ( 16 é o dano causado no inimigo com o tiro de potencia 3), o robô verifica qual potencia necessária pro tiro eliminar o adversário, evitando assim desperdício de energia. Junto com a definição de potência, foi alterada a cor do tiro, de acordo com a potência de cada.
Outro fator do aspecto artilharia é a Pontaria. O Sentufumo foi programado para travar o scanner no alvo, fazendo com que o robô se foque em um único inimigo, até eliminá-lo. Claro que, se por acaso outro inimigo cruze no campo de alcance do radar, o Sentufumo pode mudar o alvo.
Outra tática também adotada e de extrema importância, foi a de atirar com precisão. Tentar prever a movimentação inimiga, e com base na direção, velocidade do inimigo, velocidade do próprio tiro, é calculada uma possível coordenada na qual a bala atinja o inimigo quando o mesmo estiver no local em um breve espaço de tempo. Calculado isso, o canhão é girado para a devida coordenada.
