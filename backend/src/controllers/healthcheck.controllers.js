const healthCheck = async (req, res) => {
  return res
  .status(200)
  .json("everything working fine");
}

export { healthCheck };