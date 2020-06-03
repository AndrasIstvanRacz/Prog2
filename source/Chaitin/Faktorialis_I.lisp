(princ "Szam: ")
(setq z (read))
(setq y 1)
(loop for x from 1 to z
	do (setq y (* y x)))
(princ y)