calculateBasicRequiredFuel :: Int -> Int
calculateBasicRequiredFuel mass = mass `div` 3 - 2

calculateRequiredFuel mass = 
  let requiredF = calculateBasicRequiredFuel mass
      nextRequiredF = if calculateBasicRequiredFuel requiredF < 0 then 0 else calculateRequiredFuel requiredF
  in requiredF + nextRequiredF

toInt :: [String] -> [Int]
toInt values = map read values

main = do
  file <- readFile "./input/day1.txt"
  let values = toInt (lines file)
  print $ sum (map calculateRequiredFuel values)
