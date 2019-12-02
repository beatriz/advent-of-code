requiredFuel1 :: Int -> Int
requiredFuel1 mass = mass `div` 3 - 2

requiredFuel mass = 
  let nextMass = requiredFuel1 mass
  in if requiredFuel1 nextMass < 0 then nextMass else nextMass + requiredFuel nextMass

toInt :: [String] -> [Int]
toInt values = map read values

main = do
  file <- readFile "./input/day1.txt"
  let values = toInt (lines file)
  print $ sum (map requiredFuel values)
